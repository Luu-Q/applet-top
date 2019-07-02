//package com.applet.common.httpclent.service;
//
//import ai.zile.omp.content.config.RestErrorHandler;
//import ai.zile.omp.content.httpclient.XMLYParamsUtil;
//import ai.zile.omp.content.httpclient.xmlybase.XmlyHttpServiceIdEnum;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.Map;
//
//
///**
// * 喜马拉雅统一请求
// *
// * @author ln
// * @version 2019年06月27日11:39:35
// */
//@Service
//public class XmlyHttpService {
//
//    private static final Logger logger = LoggerFactory.getLogger(XmlyHttpService.class);
//
//    @Value("${thirdparty.xmly.basedomain}")
//    private String XMLY_BASE_DOMAIN;
//    @Value("${thirdparty.xmly.mobile-track-domain}")
//    private String MOBILE_TRACK_DOMAIN;
//    @Value("${thirdparty.xmly.app_key}")
//    private String XMLY_APP_KEY;
//    @Value("${thirdparty.xmly.app_secret}")
//    private String XMLY_APP_SECRET;
//    @Value("${thirdparty.xmly.server_auth_key}")
//    private String XMLY_SERVER_AUTH_KEY;
//    @Value("${thirdparty.xmly.device_id}")
//    private String DEVICE_ID;
//
//    @Autowired
//    RestTemplate restTemplate;
//
//
//    public JSONObject getAudioByTrackId(Map<String, String> request) {
//        logger.info("[喜马拉雅]根据音频id获取音频信息...");
//        return coreGetHttpTrack(JSONObject.class, XmlyHttpServiceIdEnum.MOBILE_TRACK_BASEINFO.getServiceId(), request);
//    }
//
//    public JSONObject ALBUMS(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取指定id集合的album列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ALBUMS.getServiceId(), request);
//    }
//
//    public JSONObject ALBUMS_ID(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的album...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ALBUMS_ID.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject ALBUMS_ID_TRACKS(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id专辑的音频列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ALBUMS_ID_TRACKS.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject ANNOUNCERS_ID(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的主播...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ANNOUNCERS_ID.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject ANNOUNCERS(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取指定id的主播列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ANNOUNCERS.getServiceId(), request);
//    }
//
//    public JSONObject ANNOUNCERS_ID_ALBUMS(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的主播的专辑列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ANNOUNCERS_ID_ALBUMS.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject ANNOUNCERS_ID_TOP_TRACKS(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的主播的畅销专辑列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ANNOUNCERS_ID_TOP_TRACKS.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject ANNOUNCERS_ID_RELATED_ANNOUNCERS(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id专辑的音频列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ANNOUNCERS_ID_RELATED_ANNOUNCERS.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject TRACKS_ID(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的音频...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.TRACKS_ID.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject TRACKS(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取指定id的音频列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.TRACKS.getServiceId(), request);
//    }
//
//    public JSONObject TRACKS_TRACK_SEQ(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取指定id和后续的音频列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.TRACKS_TRACK_SEQ.getServiceId(), request);
//    }
//
//    public JSONObject BROWSE_HOT_WORDS(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取 top-n 热搜词...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.BROWSE_HOT_WORDS.getServiceId(), request);
//    }
//
//    public JSONObject BROWSE_RELATIVE_ALBUMS(Map<String, String> request) {
//        logger.info("[喜马拉雅]根据参数获取专辑或音频的相关专辑的列表信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.BROWSE_RELATIVE_ALBUMS.getServiceId(), request);
//    }
//
//    public JSONObject BROWSE_ALBUM_CATEGORIES(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取喜马拉雅专辑的分类列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.BROWSE_ALBUM_CATEGORIES.getServiceId(), request);
//    }
//
//    public JSONObject BROWSE_ANNOUNCER_CATEGORIES(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取喜马拉雅主播的分类列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.BROWSE_ANNOUNCER_CATEGORIES.getServiceId(), request);
//    }
//
//    public JSONObject BROWSE_RADIO_CATEGORIES(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取喜马拉雅广播电台的分类列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.BROWSE_RADIO_CATEGORIES.getServiceId(), request);
//    }
//
//    public JSONObject ALBUM_CATEGORIES_ID(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]根据喜马拉雅专辑分类ID获取分类信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ALBUM_CATEGORIES_ID.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject ANNOUNCER_CATEGORIES_ID(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]根据喜马拉雅主播分类ID获取分类信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ANNOUNCER_CATEGORIES_ID.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject RADIO_CATEGORIES_ID(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]根据喜马拉雅广播电台分类ID获取分类信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RADIO_CATEGORIES_ID.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject ALBUM_CATEGORIES_ID_TAGS(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的album...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ALBUM_CATEGORIES_ID_TAGS.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject ALBUM_CATEGORIES_ID_ALBUMS(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取某个喜马拉雅点播分类下的标签列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.ALBUM_CATEGORIES_ID_ALBUMS.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject RADIO_CATEGORIES_ID_RADIOS(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定广播分类下的广播电台列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RADIO_CATEGORIES_ID_RADIOS.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject RECOMMENDATIONS(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取推荐专辑列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RECOMMENDATIONS.getServiceId(), request);
//    }
//
//    public JSONObject RANK(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取榜单列表信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RANK.getServiceId(), request);
//    }
//
//    public JSONObject RANK_ID(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定榜单下的专辑或音频列表信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RANK_ID.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject SEARCH_ALBUMS(Map<String, String> request) {
//        logger.info("[喜马拉雅]搜索专辑信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.SEARCH_ALBUMS.getServiceId(), request);
//    }
//
//    public JSONObject SEARCH_ANNOUNCERS(Map<String, String> request) {
//        logger.info("[喜马拉雅]搜索主播信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.SEARCH_ANNOUNCERS.getServiceId(), request);
//    }
//
//    public JSONObject SEARCH_RADIOS(Map<String, String> request) {
//        logger.info("[喜马拉雅]搜索广播信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.SEARCH_RADIOS.getServiceId(), request);
//    }
//
//    public JSONObject SEARCH_TRACKS(Map<String, String> request) {
//        logger.info("[喜马拉雅]搜索音频信息...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.SEARCH_TRACKS.getServiceId(), request);
//    }
//
//    public JSONObject PLAY_RECORDS(Map<String, String> request) {
//        logger.info("[喜马拉雅]批量上传点播播放记录...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.PLAY_RECORDS.getServiceId(), request);
//    }
//
//    public JSONObject BROWSE_AREA_RADIOS(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取指定id的album...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.BROWSE_AREA_RADIOS.getServiceId(), request);
//    }
//
//    public JSONObject BROWSE_NETWORK_RADIOS(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取国家和省市电台列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.BROWSE_NETWORK_RADIOS.getServiceId(), request);
//    }
//
//    public JSONObject RADIOS_ID(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的电台...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RADIOS_ID.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject RADIOS(Map<String, String> request) {
//        logger.info("[喜马拉雅]获取指定ids的电台列表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RADIOS.getServiceId(), request);
//    }
//
//    public JSONObject RADIOS_ID_SCHEDULES(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的电台某一天的节目排期表...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RADIOS_ID_SCHEDULES.getServiceId().replace("{id}", id), request);
//    }
//
//    public JSONObject RADIOS_ID_PLAYING_PROGRAM(Map<String, String> request, String id) {
//        logger.info("[喜马拉雅]获取指定id的电台正在播放的节目...");
//        return coreGetHttp(JSONObject.class, XmlyHttpServiceIdEnum.RADIOS_ID_PLAYING_PROGRAM.getServiceId().replace("{id}", id), request);
//    }
//
//
//    /**
//     * 喜马拉雅 Get 统一请求
//     *
//     * @param response
//     * @param serviceId
//     * @param request
//     * @return
//     */
//    private JSONObject coreGetHttp(Class response, String serviceId, Map<String, String> request) {
//        Map<String, String> reqMap = XMLYParamsUtil.paramsMap(request, XMLY_APP_KEY, XMLY_APP_SECRET, XMLY_SERVER_AUTH_KEY, DEVICE_ID);
//
//        logger.info("++++++++++++++++++++++++喜马拉雅 Get请求开始++++++++++++++++++++++++++++++");
//        logger.info("[喜马拉雅]Get请求参数：{}", reqMap);
//        logger.info("[喜马拉雅]Get请求接口：" + XMLY_BASE_DOMAIN + serviceId);
//
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(XMLY_BASE_DOMAIN + serviceId);
//        if (reqMap != null) {
//            for (Map.Entry<String, String> rmap : reqMap.entrySet()) {
//                builder.queryParam(rmap.getKey(), rmap.getValue());
//            }
//        }
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity httpEntity = new HttpEntity<>(headers);
//        String uri = builder.build().encode().toUri().toString();
//        logger.info("[喜马拉雅]Get请求uri：" + uri);
//
//        restTemplate.setErrorHandler(new RestErrorHandler());
//        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, String.class);
//
//        String resultJson = responseEntity.getBody();
//        logger.info("++++++++++++++++++++++++喜马拉雅 Get请求结束++++++++++++++++++++++++++++++");
//
//
//        logger.info("[喜马拉雅]Get请求返回值：" + resultJson);
//        JSONObject jsonObject = JSON.parseObject(resultJson);
//        return jsonObject;
//    }
//
//    /**
//     * 获取音频 Get 统一请求
//     *
//     * @param response
//     * @param serviceId
//     * @param request
//     * @return
//     */
//    private JSONObject coreGetHttpTrack(Class response, String serviceId, Map<String, String> request) {
//
//        logger.info("++++++++++++++++++++++++[获取音频] Get请求开始++++++++++++++++++++++++++++++");
//        logger.info("[获取音频]Get请求参数：{}", request);
//        logger.info("[获取音频]Get请求接口：" + XMLY_BASE_DOMAIN + serviceId);
//
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MOBILE_TRACK_DOMAIN + serviceId);
//        if (request != null) {
//            for (Map.Entry<String, String> rmap : request.entrySet()) {
//                builder.queryParam(rmap.getKey(), rmap.getValue());
//            }
//        }
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity httpEntity = new HttpEntity<>(headers);
//        String uri = builder.build().encode().toUri().toString();
//        logger.info("[获取音频]Get请求uri：" + uri);
//
//        restTemplate.setErrorHandler(new RestErrorHandler());
//        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, String.class);
//
//        String resultJson = responseEntity.getBody();
//        logger.info("++++++++++++++++++++++++获取音频 Get请求结束++++++++++++++++++++++++++++++");
//
//
//        logger.info("[获取音频]Get请求返回值：" + resultJson);
//        JSONObject jsonObject = JSON.parseObject(resultJson);
//        return jsonObject;
//    }
//
//}
