//package com.applet.sms.common;
//
//import ai.zile.common.ErrorCode;
//import ai.zile.common.omp.TokenInfo;
//import ai.zile.omp.content.dao.AlbumDao;
//import ai.zile.omp.content.dao.AudioDao;
//import ai.zile.omp.content.dao.AudioFullLogDao;
//import ai.zile.omp.content.domain.Album;
//import ai.zile.omp.content.domain.Audio;
//import ai.zile.omp.content.domain.AudioFullLog;
//import ai.zile.omp.content.domain.PageBean;
//import ai.zile.omp.content.domain.dto.*;
//import ai.zile.omp.content.exception.ContentOMPException;
//import ai.zile.omp.content.util.Validators;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Maps;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class XmlyAlbumService {
//
//    @Value("${thirdparty.xmly.logo}")
//    private String VENDOR_LOGO_URL;
//    @Autowired
//    XmlyHttpService xmlyHttpService;
//    @Autowired
//    AlbumService albumService;
//    @Autowired
//    AudioService audioService;
//    @Autowired
//    AlbumDao albumDao;
//    @Autowired
//    OSSService ossService;
//    @Autowired
//    RedisTemplate redisTemplate;
//    @Autowired
//    FullXmlyAudioTaskUtils fullXmlyAudioTaskUtils;
//    @Autowired
//    AudioFullLogDao audioFullLogDao;
//    @Autowired
//    AudioDao audioDao;
//
//    static final String XMLY_VENDOR = "xmly";
//    static final String XMLY_AUDIO_ADDED = "xmly_album_audio_added_";//已添加过的喜马拉雅专辑下的音频id
//
//
//    public JSONObject browseAlbumCategories(Map<String, String> map) {
//        JSONObject baseServiceResp = xmlyHttpService.BROWSE_ALBUM_CATEGORIES(map);
//
//        return baseServiceResp;
//    }
//
//    public PageDTO<AlbumFull> searchAlbums(Map<String, String> map) {
//        PageDTO<AlbumFull> baseServiceResp = xmlyHttpService.SEARCH_ALBUMS(map);
//
//        return baseServiceResp;
//    }
//
//    public PageDTO<JSONObject> albumsIdTracks(Map<String, String> map, String albumId) {
//        PageDTO<JSONObject> baseServiceResp = xmlyHttpService.ALBUMS_ID_TRACKS(map, albumId);
//        return baseServiceResp;
//    }
//
//    public int addXmlyAlbum(AlbumDTO albumDTO, TokenInfo tokenInfo) {
//        //上传专辑图片
//        String fileName = albumDTO.getImageUrl().split("!")[0];
//        String imageUrl = "";
//        try {
//            imageUrl = ossService.uploadImage(ossService.getUrlByte(albumDTO.getImageUrl()), "album", fileName);
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            throw new ContentOMPException(ErrorCode.UPLOAD_IMAGE_ERROR);
//        }
//
//        //专辑入库
//        Album album = new Album();
//        BeanUtils.copyProperties(albumDTO, album);
//        album.setAlbumVendor(XMLY_VENDOR);
//        album.setAlbumResId(albumDTO.getId());
//        album.setImageUrl(imageUrl);
//        int albumId = albumService.addAlbum(album, tokenInfo);
//        return albumId;
//    }
//
//    //批量添加全量拉取失败的音频
//    public void addXmlyAudioBatch(Integer albumId, TokenInfo tokenInfo) {
//        AudioFullLog audioFullLog = audioFullLogDao.getAudioFullLog(albumId);
//        String audioIds = audioFullLog.getAudioIds();
//        String[] split = audioIds.split(",");
//        List<String> list = Arrays.stream(split)
//                .filter(id -> !id.trim().isEmpty())
//                .map(id -> id.trim())
//                .collect(Collectors.toList());
//
//        List<AudioDTO> audioDTOList = new ArrayList();
//        for (String audioId :list){
//            String[] split1 = audioId.split("-");
//            Map<String, String> map = Maps.newHashMap();
//            TrackFull trackFull = getTrackInfoById(map, String.valueOf(split1[0]));//该接口为返回音频下标
//            AudioDTO audioDTO = new AudioDTO();
//            audioDTO.setAlbumId((int) trackFull.getAlbum().getId());
//            audioDTO.setAudioIndex(Integer.valueOf(split1[1]));
//            audioDTO.setAlbumName(trackFull.getAlbum().getTitle());
//            audioDTO.setUId(trackFull.getAnnouncer().getId());
//            audioDTO.setTrackId(String.valueOf(trackFull.getId()));
//            audioDTO.setDuration(trackFull.getDuration());
//            audioDTO.setTokenInfo(tokenInfo);
//            audioDTO.setAudioSum(trackFull.getAlbum().getIncludeTrackCount());
//            if(StringUtils.isNotEmpty(trackFull.getImage().getUrl())){
//                audioDTO.setAudioImgUrl(trackFull.getImage().getUrl());
//            }else{
//                audioDTO.setAudioImgUrl(trackFull.getAlbum().getCover().getMiddle().getUrl());
//            }
//            audioDTOList.add(audioDTO);
//        }
//
//        for (AudioDTO audioDTO : audioDTOList) {
//            Integer result = addXmlyAudio(audioDTO, tokenInfo);
//            if (null != result && result == 1){//成功则更新状态
//                audioFullLogDao.updateErrRetry(audioDTO.getTrackId()+"-"+audioDTO.getAudioIndex(),audioDTO.getAlbumId());
//            }
//        }
//    }
//
//    //全量添加专辑下的音频
//    public int addFullXmlyAudio(JSONObject json, TokenInfo tokenInfo) {
//
//        json.put("tokenInfo",tokenInfo);
//
//        fullXmlyAudioTaskUtils.addPackage(json);
//
//
//        return 0;
//    }
//
//    public Integer addXmlyAudio(AudioDTO audioDTO, TokenInfo tokenInfo) {
//        //1、调用移动端接口获取音频资源
//        Map<String, String> map = Maps.newHashMap();
//        map.put("device", "iPhone");
//        map.put("trackId", audioDTO.getTrackId());
//        JSONObject audioTrack = xmlyHttpService.getAudioByTrackId(map);
//        if (audioTrack.getIntValue("ret") != 0) {
//            log.error("[喜马拉雅]获取音频资源失败---->audioDTO={}，result={}", audioDTO, audioTrack);
//            return null;
//        }
//
//        //2、上传音频图片
//        JSONObject trackInfo = audioTrack.getJSONObject("trackInfo");
//        String fileName = audioDTO.getAudioImgUrl().split("!")[0];
//        String imageUrl = "";
//        try {
//            imageUrl = ossService.uploadImage(ossService.getUrlByte(audioDTO.getAudioImgUrl()), "audio", fileName);
//        } catch (Exception e) {
//            log.error("[喜马拉雅]上传音频图片失败---->audioDTO={}", audioDTO, e);
//            return null;
//        }
//
//        //3、上传音频到临时文件
//        String audioUrl = "";
//        try {
//            audioUrl = ossService.uploadAudio(ossService.getUrlByte(trackInfo.getString("playUrl64")), trackInfo.getString("playUrl64"));
//        } catch (Exception e) {
//            log.error("[喜马拉雅]上传音频失败---->audioDTO={}", audioDTO, e);
//            return null;
//        }
//
//        //查询专辑表专辑
//        Album album = albumDao.findAlbumResIdVendor(trackInfo.getIntValue("albumId"), XMLY_VENDOR);
//        //4、封装音频对象
//        Audio audio = new Audio();
//        BeanUtils.copyProperties(audioDTO, audio);
//        audio.setAlbumId(album.getAlbumId());
//        audio.setAudioType(album.getAlbumType());
//        audio.setAudioVendor(XMLY_VENDOR);
//        audio.setAudioResId(trackInfo.getIntValue("trackId"));
//        audio.setAudioName(trackInfo.getString("title"));
//        audio.setAnnouncerId(audioDTO.getUId());
//        audio.setAudioResUrl(trackInfo.getString("playUrl64"));
//        audio.setImageUrl(imageUrl);
//        audio.setAudioUrl(audioUrl);
//        audio.setVendorLogoUrl(VENDOR_LOGO_URL);
//
//        //5、入库，拷贝文件到oss，添加到ES
//        Integer result = null;
//        try {
//            result = audioService.addXmlyAudio(audio, album, tokenInfo);
//        } catch (Exception e) {
//            log.error("[喜马拉雅]音频入库失败---->audioDTO={}", audioDTO, e);
//            return null;
//        }
//        return result;
//    }
//
//
//    public JSONObject getAudioByTrackId(Map<String, String> map) {
//        JSONObject audioTrack = xmlyHttpService.getAudioByTrackId(map);
//        return audioTrack;
//    }
//
//    public TrackFull getTrackInfoById(Map<String, String> map, String id) {
//        TrackFull baseServiceResp = xmlyHttpService.TRACKS_ID(map, id);
//        return baseServiceResp;
//    }
//
//    public PageBean<AudioFullLog> getAudioSyncList(int offset, int limit) {
//        int total = audioFullLogDao.findAll();
//        PageBean<AudioFullLog> pb = new PageBean<>(offset, limit, total);
//        int startIndex = pb.getStartIndex();
//        pb.setList(audioFullLogDao.getAudioSyncList(startIndex, limit));
//        return pb;
//    }
//
//
//    public boolean isAddFullXmlyAudio(String key, String value) {
//        if(Validators.isExist(key + value)) {
//            return true;
//        }
//
//        //已添加过音频的专辑不能使用全量添加，否则会重复
//        if (audioDao.findAlbumResIdVendor(value, XMLY_VENDOR) == 0) {
//            return false;
//        }
//        return true;
//    }
//
//    public boolean isAddXmlyAlbum(String key, String value) {
//        if(Validators.isExist(key + value)) {
//            return true;
//        }
//
//        //如果添加则不再添加，否则会重复
//        if (albumDao.findAlbumResIdVendor(Integer.valueOf(value), XMLY_VENDOR) == null) {
//            return false;
//        }
//        return true;
//    }
//}
