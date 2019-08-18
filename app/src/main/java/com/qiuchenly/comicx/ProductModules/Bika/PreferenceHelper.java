package com.qiuchenly.comicx.ProductModules.Bika;

import android.content.Context;

import java.util.Set;

public class PreferenceHelper {
    public static final String KEY_AD_URL_BASE = "KEY_AD_URL_BASE";
    public static final String KEY_CHANNEL = "KEY_CHANNEL";
    public static final String KEY_CHATROOM_SETTING_ADS_INTERVAL = "KEY_CHATROOM_SETTING_ADS_INTERVAL_2";
    public static final String KEY_CHATROOM_SETTING_BLACK_LIST_USER = "KEY_CHATROOM_SETTING_BLACK_LIST_USER";
    public static final String KEY_CHATROOM_SETTING_COLOR_REVERSE = "KEY_CHATROOM_SETTING_COLOR_REVERSE";
    public static final String KEY_CHATROOM_SETTING_CUSTOM_AVATAR = "KEY_CHATROOM_SETTING_CUSTOM_AVATAR";
    public static final String KEY_CHATROOM_SETTING_CUSTOM_NAME = "KEY_CHATROOM_SETTING_CUSTOM_NAME";
    public static final String KEY_CHATROOM_SETTING_FIX_IMAGE_SIZE = "KEY_CHATROOM_SETTING_FIX_IMAGE_SIZE";
    public static final String KEY_CHATROOM_SETTING_HIDE_ALL_AVATAR = "KEY_CHATROOM_SETTING_HIDE_ALL_AVATAR";
    public static final String KEY_CHATROOM_SETTING_MAX_MESSAGE_SIZE = "KEY_CHATROOM_SETTING_MAX_MESSAGE_SIZE";
    public static final String KEY_CHATROOM_SETTING_NIGHT_MODE = "KEY_CHATROOM_SETTING_NIGHT_MODE";
    public static final String KEY_CHATROOM_SETTING_SHOW_TIMESTAMP = "KEY_CHATROOM_SETTING_SHOW_TIMESTAMP";
    public static final String KEY_CHATROOM_SETTING_SPEECH = "KEY_CHATROOM_SETTING_SPEECH";
    public static final String KEY_CHATROOM_SETTING_SPEECH_LANGUAGE = "KEY_CHATROOM_SETTING_SPEECH_LANGUAGE";
    public static final String KEY_CHATROOM_SETTING_SPEECH_WITH_NAME = "KEY_CHATROOM_SETTING_SPEECH_WITH_NAME";
    public static final String KEY_COMIC_VIEWER_AUTO_BRIGHTNESS = "KEY_COMIC_VIEWER_AUTO_BRIGHTNESS";
    public static final String KEY_COMIC_VIEWER_AUTO_SCROLL_TIME_INTERVAL = "KEY_COMIC_VIEWER_AUTO_SCROLL_TIME_INTERVAL";
    public static final String KEY_COMIC_VIEWER_BRIGHTNESS_INDEX = "KEY_COMIC_VIEWER_BRIGHTNESS_INDEX";
    public static final String KEY_COMIC_VIEWER_IMAGE_QUALITY = "KEY_COMIC_VIEWER_IMAGE_QUALITY";
    public static final String KEY_COMIC_VIEWER_NIGHT_MODE = "KEY_COMIC_VIEWER_NIGHT_MODE";
    public static final String KEY_COMIC_VIEWER_TESTING_VERSION = "KEY_COMIC_VIEWER_TESTING_VERSION";
    public static final String KEY_COMIC_VIEWER_VERTICAL_SCREEN_ORIENTATION = "KEY_COMIC_VIEWER_VERTICAL_SCREEN_ORIENTATION";
    public static final String KEY_COMIC_VIEWER_VERTICAL_SCROLL_DIRECTION = "KEY_COMIC_VIEWER_VERTICAL_SCROLL_DIRECTION";
    public static final String KEY_COMIC_VIEWER_VOLUME_PAGING_CONTROL = "KEY_COMIC_VIEWER_VOLUME_PAGING_CONTROL";
    public static final String KEY_DNS_IP = "KEY_DNS_IP";
    public static final String KEY_FILTER_ON_OFF_INDEX_BL = "KEY_FILTER_ON_OFF_INDEX_BL";
    public static final String KEY_FILTER_ON_OFF_INDEX_FAKE_GIRL = "KEY_FILTER_ON_OFF_INDEX_FAKE_GIRL";
    public static final String KEY_FILTER_ON_OFF_INDEX_FORBIDDEN = "KEY_FILTER_ON_OFF_INDEX_FORBIDDEN";
    public static final String KEY_FILTER_ON_OFF_INDEX_FUTARI = "KEY_FILTER_ON_OFF_INDEX_FUTARI";
    public static final String KEY_FILTER_ON_OFF_INDEX_HEAVY = "KEY_FILTER_ON_OFF_INDEX_HEAVY";
    public static final String KEY_FILTER_ON_OFF_INDEX_JAPANESE = "KEY_FILTER_ON_OFF_INDEX_JAPANESE";
    public static final String KEY_FILTER_ON_OFF_INDEX_PURE_LOVE = "KEY_FILTER_ON_OFF_INDEX_PURE_LOVE";
    public static final String KEY_FILTER_ON_OFF_INDEX_WEBTOON = "KEY_FILTER_ON_OFF_INDEX_WEBTOON";
    public static final String[] KEY_FILTER_ON_OFF = new String[]{
            PreferenceHelper.KEY_FILTER_ON_OFF_INDEX_FORBIDDEN,
            PreferenceHelper.KEY_FILTER_ON_OFF_INDEX_JAPANESE,
            PreferenceHelper.KEY_FILTER_ON_OFF_INDEX_BL,
            PreferenceHelper.KEY_FILTER_ON_OFF_INDEX_HEAVY,
            PreferenceHelper.KEY_FILTER_ON_OFF_INDEX_PURE_LOVE,
            PreferenceHelper.KEY_FILTER_ON_OFF_INDEX_FAKE_GIRL,
            PreferenceHelper.KEY_FILTER_ON_OFF_INDEX_FUTARI,
            PreferenceHelper.KEY_FILTER_ON_OFF_INDEX_WEBTOON
    };
    public static final String KEY_IMAGE_STORAGE = "KEY_IMAGE_STORAGE";
    public static final String KEY_IS_GIRL = "KEY_IS_GIRL";
    public static final String KEY_LAST_ANNOUNCEMENT_ID = "KEY_LAST_ANNOUNCEMENT_ID";
    public static final String KEY_LAST_POPUP_ADS_TIMESTAMP = "KEY_LAST_POPUP_ADS_TIMESTAMP";
    public static final String KEY_LOCAL_API_DATA_ANNOUNCEMTN_LIST = "KEY_LOCAL_API_DATA_ANNOUNCEMTN_LIST";
    public static final String KEY_LOCAL_API_DATA_BANNER_LIST = "KEY_LOCAL_API_DATA_BANNER_LIST";
    public static final String KEY_LOCAL_API_DATA_CATEGORY_LIST = "KEY_LOCAL_API_DATA_CATEGORY_LIST";
    public static final String KEY_LOCAL_API_DATA_CHATROOM_LIST = "KEY_LOCAL_API_DATA_CHATROOM_LIST";
    public static final String KEY_LOCAL_API_DATA_IP = "LOCAL_API_DATA_IP";
    public static final String KEY_LOCAL_API_DATA_PROFILE = "KEY_LOCAL_API_DATA_PROFILE";
    public static final String KEY_LOCK_PASSWORD = "KEY_LOCK_PASSWORD";
    public static final String KEY_PERFORMANCE_ENHANCEMENT = "KEY_PERFORMANCE_ENHANCEMENT";
    public static final String KEY_SHOW_FAQ = "KEY_SHOW_FAQ";
    public static final String KEY_SHOW_HINT_COMIC_VIEWER_SETTING = "KEY_SHOW_HINT_COMIC_VIEWER_SETTING";
    public static final String KEY_THEME_COLOR = "KEY_THEME_COLOR";
    public static final String KEY_TIME_DIFFERENCE = "KEY_TIME_DIFFERENCE";
    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_USER_LEVEL = "KEY_USER_LEVEL";
    public static final String KEY_USER_LOGIN_EMAIL = "KEY_USER_LOGIN_EMAIL";
    public static final String KEY_USER_LOGIN_PASSWORD = "KEY_USER_LOGIN_PASSWORD";
    public static final int PREFERENCE_MODE = 0;
    public static final String PREFERENCE_NAME = "PICACOMIC_FREGATA";
    public static final String IS_FIRST = "IS_FIRST";

    public static String getUserLoginEmail(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_USER_LOGIN_EMAIL, "");
    }

    public static boolean getIsFirst(Context context) {
        boolean isFirst = context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(IS_FIRST, true);
        if (isFirst)
            context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(IS_FIRST, false).apply();
        return isFirst;
    }

    public static boolean setUserLoginEmail(Context context, String email) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_USER_LOGIN_EMAIL, email).commit();
    }

    public static String getUserLoginPassword(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_USER_LOGIN_PASSWORD, "");
    }

    public static boolean setUserLoginPassword(Context context, String password) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_USER_LOGIN_PASSWORD, password).commit();
    }

    public static boolean isComicViewerTestingVersion(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_COMIC_VIEWER_TESTING_VERSION, false);
    }

    public static boolean setComicViewerTestingVersion(Context context, boolean isTestingVersion) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_COMIC_VIEWER_TESTING_VERSION, isTestingVersion).commit();
    }

    public static boolean isPerformanceEnhancement(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_PERFORMANCE_ENHANCEMENT, true);
    }

    public static boolean setPerformanceEnhancement(Context context, boolean performanceEnhancement) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_PERFORMANCE_ENHANCEMENT, performanceEnhancement).commit();
    }

    public static String getLockPassword(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_LOCK_PASSWORD, "");
    }

    public static boolean setLockPassword(Context context, String password) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_LOCK_PASSWORD, password).commit();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_TOKEN, "");
    }

    public static boolean setToken(Context context, String token) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_TOKEN, token).commit();
    }

    public static int getUserLevel(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_USER_LEVEL, -1);
    }

    public static boolean setUserLevel(Context context, int level) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_USER_LEVEL, level).commit();
    }

    public static String getLocalApiDataProfile(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_LOCAL_API_DATA_PROFILE, "");
    }

    public static boolean setLocalApiDataProfile(Context context, String apiString) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_LOCAL_API_DATA_PROFILE, apiString).commit();
    }

    public static String getLocalApiDataCategoryList(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_LOCAL_API_DATA_CATEGORY_LIST, "");
    }

    /**
     * 设置为false打开认证页
     *
     * @param context
     * @param value
     * @return
     */
    public static boolean setNoLoginBika(Context context, boolean value) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean("NO_LOGIN_BIKA", value).commit();
    }

    public static boolean getNoLoginBika(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean("NO_LOGIN_BIKA", true);
    }

    public static boolean setLocalApiDataCategoryList(Context context, String apiString) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_LOCAL_API_DATA_CATEGORY_LIST, apiString).commit();
    }

    public static String getLocalApiDataAnnouncemtnList(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_LOCAL_API_DATA_ANNOUNCEMTN_LIST, "");
    }

    public static boolean setLocalApiDataAnnouncemtnList(Context context, String apiString) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_LOCAL_API_DATA_ANNOUNCEMTN_LIST, apiString).commit();
    }

    public static String getLocalApiDataBannerList(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_LOCAL_API_DATA_BANNER_LIST, "");
    }

    public static boolean setLocalApiDataBannerList(Context context, String apiString) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_LOCAL_API_DATA_BANNER_LIST, apiString).commit();
    }

    public static String getLocalApiDataChatroomList(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_LOCAL_API_DATA_CHATROOM_LIST, "[{\"title\":\"嗶咔聊天室\",\"description\":\"嗶咔聊天室\",\"url\":\"https://chat.picacomic.com\"}]");
    }

    public static boolean setLocalApiDataChatroomList(Context context, String apiString) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_LOCAL_API_DATA_CHATROOM_LIST, apiString).commit();
    }

    public static String getLocalApiDataIp(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_LOCAL_API_DATA_IP, "[{\"title\":\"嗶咔聊天室\",\"description\":\"嗶咔聊天室\",\"url\":\"https://chat.picacomic.com\"}]");
    }

    public static boolean setLocalApiDataIp(Context context, String apiString) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_LOCAL_API_DATA_CHATROOM_LIST, apiString).commit();
    }

    public static String getLastAnnouncementId(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_LAST_ANNOUNCEMENT_ID, "");
    }

    public static boolean setLastAnnouncementId(Context context, String announcementId) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_LAST_ANNOUNCEMENT_ID, announcementId).commit();
    }

    public static long getTimeDifference(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getLong(KEY_TIME_DIFFERENCE, 0);
    }

    public static boolean setTimeDifference(Context context, long time) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putLong(KEY_TIME_DIFFERENCE, time).commit();
    }

    public static boolean isShowHintComicViewerSetting(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_SHOW_HINT_COMIC_VIEWER_SETTING, true);
    }

    public static boolean setShowHintComicViewerSetting(Context context, boolean showHint) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_SHOW_HINT_COMIC_VIEWER_SETTING, showHint).commit();
    }

    public static boolean isNightMode(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_COMIC_VIEWER_NIGHT_MODE, false);
    }

    public static boolean setNightMode(Context context, boolean isNightMode) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_COMIC_VIEWER_NIGHT_MODE, isNightMode).commit();
    }

    public static boolean isAutoBrightness(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_COMIC_VIEWER_AUTO_BRIGHTNESS, true);
    }

    public static boolean setAutoBrightness(Context context, boolean isAutoBrightness) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_COMIC_VIEWER_AUTO_BRIGHTNESS, isAutoBrightness).commit();
    }

    public static int getBrightnessIndex(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_COMIC_VIEWER_BRIGHTNESS_INDEX, 1000);
    }

    public static boolean setBrightnessIndex(Context context, int brightnessIndex) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_COMIC_VIEWER_BRIGHTNESS_INDEX, brightnessIndex).commit();
    }

    public static boolean isVerticalScreenOrientation(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_COMIC_VIEWER_VERTICAL_SCREEN_ORIENTATION, true);
    }

    public static boolean setVerticalScreenOrientation(Context context, boolean isVerticalScreenOrientation) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_COMIC_VIEWER_VERTICAL_SCREEN_ORIENTATION, isVerticalScreenOrientation).commit();
    }

    public static boolean isVerticalScrollDirection(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_COMIC_VIEWER_VERTICAL_SCROLL_DIRECTION, true);
    }

    public static boolean setVerticalScrollDirection(Context context, boolean isVerticalScrollDirection) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_COMIC_VIEWER_VERTICAL_SCROLL_DIRECTION, isVerticalScrollDirection).commit();
    }

    public static int getAutoScrollTimeInterval(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_COMIC_VIEWER_AUTO_SCROLL_TIME_INTERVAL, 1000);
    }

    public static boolean setAutoScrollTimeInterval(Context context, int autoScrollTimeInterval) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_COMIC_VIEWER_AUTO_SCROLL_TIME_INTERVAL, autoScrollTimeInterval).commit();
    }

    public static long getLastPopupAdsTimestamp(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getLong(KEY_LAST_POPUP_ADS_TIMESTAMP, 0);
    }

    public static boolean setLastPopupAdsTimestamp(Context context, long lastPopupAdsTimestamp) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putLong(KEY_LAST_POPUP_ADS_TIMESTAMP, lastPopupAdsTimestamp).commit();
    }

    public static boolean isVolumePagingControlOn(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_COMIC_VIEWER_VOLUME_PAGING_CONTROL, true);
    }

    public static boolean setVolumePagingControl(Context context, boolean volumePagingControl) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_COMIC_VIEWER_VOLUME_PAGING_CONTROL, volumePagingControl).commit();
    }

    public static int getComicViewerImageQuality(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_COMIC_VIEWER_IMAGE_QUALITY, 0);
    }

    public static boolean setComicViewerImageQuality(Context context, int imageQuality) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_COMIC_VIEWER_IMAGE_QUALITY, imageQuality).commit();
    }

    public static String getImageStorage(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_IMAGE_STORAGE, null);
    }

    public static boolean setImageStorage(Context context, String imagePath) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_IMAGE_STORAGE, imagePath).commit();
    }

    public static boolean isFilterOnWithIndex(Context context, int index) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_FILTER_ON_OFF[index], false);
    }

    public static boolean setFilterOnWithIndex(Context context, int index, boolean filterOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_FILTER_ON_OFF[index], filterOn).commit();
    }

    public static boolean isFilterOnForbidden(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_FILTER_ON_OFF_INDEX_FORBIDDEN, false);
    }

    public static boolean setFilterOnForbidden(Context context, boolean filterOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_FILTER_ON_OFF_INDEX_FORBIDDEN, filterOn).commit();
    }

    public static boolean isFilterOnJapanese(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_FILTER_ON_OFF_INDEX_JAPANESE, false);
    }

    public static boolean setFilterOnJapanese(Context context, boolean filterOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_FILTER_ON_OFF_INDEX_JAPANESE, filterOn).commit();
    }

    public static boolean isFilterOnBl(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_FILTER_ON_OFF_INDEX_BL, false);
    }

    public static boolean setFilterOnBl(Context context, boolean filterOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_FILTER_ON_OFF_INDEX_BL, filterOn).commit();
    }

    public static boolean isFilterOnHeavy(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_FILTER_ON_OFF_INDEX_HEAVY, false);
    }

    public static boolean setFilterOnHeavy(Context context, boolean filterOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_FILTER_ON_OFF_INDEX_HEAVY, filterOn).commit();
    }

    public static boolean isFilterOnPureLove(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_FILTER_ON_OFF_INDEX_PURE_LOVE, false);
    }

    public static boolean setFilterOnPureLove(Context context, boolean filterOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_FILTER_ON_OFF_INDEX_PURE_LOVE, filterOn).commit();
    }

    public static boolean isFilterOnFakeGirl(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_FILTER_ON_OFF_INDEX_FAKE_GIRL, false);
    }

    public static boolean setFilterOnFakeGirl(Context context, boolean filterOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_FILTER_ON_OFF_INDEX_FAKE_GIRL, filterOn).commit();
    }

    public static boolean isFilterOnFutari(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_FILTER_ON_OFF_INDEX_FUTARI, false);
    }

    public static boolean setFilterOnFutari(Context context, boolean filterOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_FILTER_ON_OFF_INDEX_FUTARI, filterOn).commit();
    }

    public static boolean isChatroomSettingNightMode(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_CHATROOM_SETTING_NIGHT_MODE, false);
    }

    public static boolean setChatroomSettingNightMode(Context context, boolean isOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_CHATROOM_SETTING_NIGHT_MODE, isOn).commit();
    }

    public static boolean isChatroomSettingShowTimestamp(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_CHATROOM_SETTING_SHOW_TIMESTAMP, true);
    }

    public static boolean setChatroomSettingShowTimestamp(Context context, boolean isOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_CHATROOM_SETTING_SHOW_TIMESTAMP, isOn).commit();
    }

    public static boolean isChatroomSettingFixImageSize(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_CHATROOM_SETTING_FIX_IMAGE_SIZE, false);
    }

    public static boolean setChatroomSettingFixImageSize(Context context, boolean isOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_CHATROOM_SETTING_FIX_IMAGE_SIZE, isOn).commit();
    }

    public static int getChatroomSettingMaxMessageSize(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_CHATROOM_SETTING_MAX_MESSAGE_SIZE, 100);
    }

    public static boolean setChatroomSettingMaxMessageSize(Context context, int index) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_CHATROOM_SETTING_MAX_MESSAGE_SIZE, index).commit();
    }

    public static int getChatroomSettingAdsInterval(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_CHATROOM_SETTING_ADS_INTERVAL, 30);
    }

    public static boolean setChatroomSettingAdsInterval(Context context, int index) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_CHATROOM_SETTING_ADS_INTERVAL, index).commit();
    }

    public static boolean isChatroomSettingSpeech(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_CHATROOM_SETTING_SPEECH, false);
    }

    public static boolean setChatroomSettingSpeech(Context context, boolean isOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_CHATROOM_SETTING_SPEECH, isOn).commit();
    }

    public static boolean isChatroomSettingSpeechWithName(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_CHATROOM_SETTING_SPEECH_WITH_NAME, false);
    }

    public static boolean setChatroomSettingSpeechWithName(Context context, boolean isOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_CHATROOM_SETTING_SPEECH_WITH_NAME, isOn).commit();
    }

    public static String getChatroomSettingSpeechLanguage(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_CHATROOM_SETTING_SPEECH_LANGUAGE, Language.CHINESE);
    }

    public static boolean setChatroomSettingLanguage(Context context, String language) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_CHATROOM_SETTING_SPEECH_LANGUAGE, language).commit();
    }

    public static String getChatroomSettingCustomAvatar(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_CHATROOM_SETTING_CUSTOM_AVATAR, "");
    }

    public static boolean setChatroomSettingCustomAvatar(Context context, String avatar) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_CHATROOM_SETTING_CUSTOM_AVATAR, avatar).commit();
    }

    public static String getChatroomSettingCustomName(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_CHATROOM_SETTING_CUSTOM_NAME, "");
    }

    public static boolean setChatroomSettingCustomName(Context context, String name) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_CHATROOM_SETTING_CUSTOM_NAME, name).commit();
    }

    public static boolean isChatroomSettingHideAllAvatar(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_CHATROOM_SETTING_HIDE_ALL_AVATAR, false);
    }

    public static boolean setChatroomSettingHideAllAvatar(Context context, boolean isOn) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_CHATROOM_SETTING_HIDE_ALL_AVATAR, isOn).commit();
    }

    public static String getChatroomSettingBlackListUser(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_CHATROOM_SETTING_BLACK_LIST_USER, null);
    }

    public static boolean setChatroomSettingBlackListUser(Context context, String blackListJson) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_CHATROOM_SETTING_BLACK_LIST_USER, blackListJson).commit();
    }

    public static int getChatroomSettingColorReverse(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_CHATROOM_SETTING_COLOR_REVERSE, 70);
    }

    public static boolean setChatroomSettingColorReverse(Context context, int index) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_CHATROOM_SETTING_COLOR_REVERSE, index).commit();
    }

    public static boolean isShowFaq(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_SHOW_FAQ, true);
    }

    public static boolean setShowFaq(Context context, boolean showFaq) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_SHOW_FAQ, showFaq).commit();
    }

    public static int getThemeColor(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_THEME_COLOR, 0);
    }

    public static boolean setThemeColor(Context context, int index) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_THEME_COLOR, index).commit();
    }

    public static String getAdUrlBase(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(KEY_AD_URL_BASE, Constants.AD_LINK_BASE);
    }

    public static boolean setAdUrlBase(Context context, String urlBase) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(KEY_AD_URL_BASE, urlBase).commit();
    }

    public static String[] getDnsIp(Context context) {
        Set<String> addressSet = context.getSharedPreferences(PREFERENCE_NAME, 0).getStringSet(KEY_DNS_IP, null);
        if (addressSet == null) {
            return null;
        }
        return addressSet.toArray(new String[addressSet.size()]);
    }

    public static boolean setDnsIp(Context context, Set<String> name) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putStringSet(KEY_DNS_IP, name).commit();
    }

    public static boolean isGirl(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(KEY_IS_GIRL, false);
    }

    public static boolean setGirl(Context context, boolean isGirl) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(KEY_IS_GIRL, isGirl).commit();
    }

    public static int getChannel(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(KEY_CHANNEL, 1);
    }

    public static boolean setChannel(Context context, int channel) {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(KEY_CHANNEL, channel).commit();
    }
}
