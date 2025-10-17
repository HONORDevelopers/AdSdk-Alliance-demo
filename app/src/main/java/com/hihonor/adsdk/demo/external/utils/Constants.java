/*
 * Copyright (c) Honor Device Co., Ltd. 2022-2022. All rights reserved.
 */

package com.hihonor.adsdk.demo.external.utils;


/**
 * 功能描述
 *
 * @since 2022-06-01
 */
public interface Constants {
    /**
     * 推广类型
     */
    interface POPULARIZE_TYPE {
        /**
         * 应用推广（下载）
         */
        int APP_POPULARIZE_DOWNLOAD = 0;

        /**
         * 网页推广
         */
        int WEB_POPULARIZE = 1;

        /**
         * 应用直达
         */
        int APP_DIRECT = 2;

        /**
         * 小程序
         */
        int APP_MINI_APP = 3;

        /**
         * 快应用推广
         */
        int QUICK_APP_POPULARIZE = 103;
    }

    interface AD_CLICK_TYPE {
        int PICTURE = 1;
        int MORE = 2;
        int JUMP_BUTTON = 3;
        int OTHER = 4;
        int VIDEO = 5;
        int TEXT = 6;
    }

    interface SUB_TYPE {
        int BIG_PICTURE = 4;
        int SMALL_PICTURE = 5;
        int THREE_PICTURE = 6;
        int APP_PICTURE = 10;
    }

    /**
     * 广告图文模板
     */
    interface TEMPLATE_TYPE {
        /**
         * 小图文非下载布局模板，左图右文 或 左文右图。
         */
        int TEMPLATE_1 = 0;
        /**
         * 小图文下载布局模板，安装器应用下载
         */
        int TEMPLATE_2 = 1;
    }

    interface LAYOUT {
        int TOP_PICTURE_BOTTOM_TEXT = 1;
        int TOP_TEXT_BOTTOM_PICTURE = 2;
        int RIGHT_TEXT_LEFT_PICTURE = 3;
        int LEFT_TEXT_RIGHT_PICTURE = 4;
    }

    interface AD_RENDERING_TYPE {
        int TEMPLATE_RENDERING = 0;//模板渲染
        int SELF_RENDERING = 1;//自渲染
    }

    /**
     * 媒体请求Type:0-普通加载方式，会首先去读缓存；
     * 1-预缓存加载，将数据保存至缓存；
     * -1表示直接进行网络请求,数据不保存缓存
     * */
    interface AD_LOAD_TYPE {
        int NORMAL_REQUEST = 0; //普通请求，优先去读缓存
        int PRECACHE_REQUEST = 1; //预缓存请求，数据保存至缓存
        int DEFAULT_REQUEST = -1; //默认请求方式，不进行缓存
    }

    interface AD_ACTION_TYPE {
        int ACTION_CLICK = 0;
        int ACTION_SLIDE_UP = 1;
        int ACTION_SHAKE = 2;
    }
    interface SCENE_TYPE {
        //广告界面
        int AD_DEFAULT = 1;

        //详情页
        int AD_DETAILS_PAGE = 0;
    }
    interface AD_ITEM_TYPE {
        int NORMAL = 0;
        int AD_VIDEO = 1;
        int CUSTOM_VIDEO = 2;
        int BIG_TOP_TEXT = 3;
        int BIG_TOP_PICTURE = 4;
        int THREE_TOP_TEXT = 5;
        int THREE_TOP_PICTURE = 6;
        int SMALL_LEFT_TEXT = 7;
        int SMALL_LEFT_PICTURE = 8;
        int APP_PICTURE = 9;
    }

    String AD_LISTENER_TAG = "ad_listener_tag";
}
