//package com.applet.sms.common;
//
//import ai.zile.common.omp.TokenInfo;
//import ai.zile.omp.content.dao.AudioFullLogDao;
//import ai.zile.omp.content.domain.AudioFullLog;
//import ai.zile.omp.content.domain.dto.AudioDTO;
//import ai.zile.omp.content.domain.dto.PageDTO;
//import ai.zile.omp.content.service.XmlyAlbumService;
//import ai.zile.omp.content.service.XmlyHttpService;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Maps;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//@Component
//public class FullXmlyAudioTaskUtils {
//    public static Logger logger = LoggerFactory.getLogger(FullXmlyAudioTaskUtils.class);
//
//    @Autowired
//    ThreadPoolTaskExecutor taskExecutor;
//    @Autowired
//    XmlyAlbumService xmlyAlbumService;
//    @Autowired
//    XmlyHttpService xmlyHttpService;
//    @Autowired
//    AudioFullLogDao audioFullLogDao;
//    public static ConcurrentLinkedQueue<JSONObject> audioQueue = new ConcurrentLinkedQueue();
//
//    private static boolean initFlag = false;
//    private static int idleCnt = 0;
//
//    /**
//     * 初始任务
//     */
//    public void initTask() {
//
//        idleCnt = 0;
//        if (initFlag) {
//
//            return;
//        }
//        initFlag = true;
//
//        logger.info("[喜马拉雅]initTask开始执行任务");
//
//        taskExecutor.execute(new Runnable() {
//
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        if (audioQueue.isEmpty()) {
//                            if (idleCnt++ == 3) {//重试机制
//                                initFlag = false;
//                                break; //结束线程任务
//                            }
//
//                        } else {
//                            idleCnt = 0;
//
//                            List<AudioDTO> audiolist = new ArrayList();
//
//                            JSONObject json = audioQueue.poll();//每次执行20个任务量
//                            int albumId = json.getIntValue("albumId");
//                            int includeTrackCount = json.getIntValue("includeTrackCount");
//                            String albumUrl = json.getString("albumUrl");
//                            TokenInfo tokenInfo = json.getJSONObject("tokenInfo").toJavaObject(TokenInfo.class);
//
//                            int limit = 20;
//                            //一次请求20条数据,共请求count次
//                            BigDecimal divide = BigDecimal.valueOf(includeTrackCount).divide(BigDecimal.valueOf(limit), 0, BigDecimal.ROUND_UP);
//                            int count = divide.intValue();
//
//                            for (int i = 0; i < count; i++) {
//                                Map<String, String> map = Maps.newHashMap();
//                                int offset = i * limit;//计算索引位置
//                                map.put("offset", String.valueOf(offset));
//                                map.put("limit", String.valueOf(limit));
//                                map.put("sort", "asc");
//                                map.put("fields", "duration,id,order_num,image,announcer,album");//返回的字段列表，⽤逗号分隔，默认表示全部
//                                PageDTO<JSONObject> tracks = xmlyHttpService.ALBUMS_ID_TRACKS(map, String.valueOf(albumId));
//                                List<JSONObject> trackFulls = tracks.getItems();
//                                if (trackFulls != null && trackFulls.size() > 0) {
//                                    for (JSONObject trackFull :trackFulls){
//                                        AudioDTO audioDTO = new AudioDTO();
//                                        audioDTO.setTrackId(String.valueOf(trackFull.getString("id")));
//                                        audioDTO.setAudioIndex(trackFull.getIntValue("orderNum"));
//                                        audioDTO.setDuration(trackFull.getIntValue("duration"));
//                                        audioDTO.setAlbumId(trackFull.getJSONObject("album").getIntValue("id"));
//                                        audioDTO.setAlbumName(trackFull.getJSONObject("album").getString("title"));
//                                        audioDTO.setAudioSum(trackFull.getJSONObject("album").getIntValue("includeTrackCount"));
//                                        JSONObject image = trackFull.getJSONObject("image");
//                                        if(StringUtils.isNotEmpty(image.getString("url"))){
//                                            audioDTO.setAudioImgUrl(image.getString("url"));
//                                        }else {
//                                            audioDTO.setAudioImgUrl(albumUrl);
//                                        }
//
//                                        JSONObject announcer = trackFull.getJSONObject("announcer");
//                                        audioDTO.setUId(announcer.getIntValue("id"));
//                                        audioDTO.setTokenInfo(tokenInfo);
//                                        audiolist.add(audioDTO);
//                                    }
//                                }
//
//                            }
//
//
//
//                            if (audiolist.size() > 0) {
//                                AudioFullLog audioFullLog = new AudioFullLog();
//                                audioFullLog.setAlbumId(audiolist.get(0).getAlbumId());
//                                audioFullLog.setAlbumName(audiolist.get(0).getAlbumName());
//                                audioFullLog.setAudioSum(audiolist.get(0).getAudioSum());
//                                audioFullLog.setSource("xmly");
//
//                                final List<AudioDTO> audioexeclist = audiolist;
//                                for (int s = 0; s < audioexeclist.size(); s++) {
//                                    int finalS = s;
//                                    taskExecutor.execute(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                Integer result = xmlyAlbumService.addXmlyAudio(audioexeclist.get(finalS), audioexeclist.get(finalS).getTokenInfo());
//                                                if (null == result || result != 1){//失败
//                                                    audioFullLog.setAudioIds(","+audioexeclist.get(finalS).getTrackId()+"-"+audioexeclist.get(finalS).getAudioIndex());
//                                                    audioFullLogDao.addOrUpdateErr(audioFullLog);
//                                                }else{//成功
//                                                    audioFullLogDao.addOrUpdateSucc(audioFullLog);
//                                                }
//                                            } catch (Exception e) {
//                                                audioFullLog.setAudioIds(","+audioexeclist.get(finalS).getTrackId());
//                                                logger.error("[喜马拉雅]全量添加音频单个任务失败--->{}",audioexeclist.get(finalS),e);
//                                            }
//
//                                        }
//                                    });
//                                }
//                            }
//                        }
//
//                        Thread.sleep(300);
//                    } catch (Exception e) {
//                        logger.error("[喜马拉雅]全量添加音频失败",e);
//                    }
//                }
//            }
//        });
//    }
//
//
//    /**
//     * 增加包
//     *
//     * @param jsonObject
//     */
//    public void addPackage(JSONObject jsonObject) {
//        audioQueue.offer(jsonObject);
//
//        if (!initFlag) {
//            initTask();
//        }
//    }
//
//
//}
