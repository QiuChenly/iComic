package com.qiuchenly.comicx.ProductModules.ComicHome;

import android.text.TextUtils;
import com.qiuchenly.comicx.Bean.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.List;

public interface ComicApi {

    String BaseLine = "?channel=Android&version=2.7.018&_debug=0";
    String BASE_API_USER = "https://user.dmzj.com";
    String BASE_API_V3API = "https://v3api.dmzj.com";

    static String getApi(int mType, String... strArr) {
        String str = "";
        switch (mType) {
            case 1:
                return "/register/verify";
            case 2:
                return "/register/m_submit_v2";
            case 3:
                return "/register/send_tel_code";
            case 4:
                return "/account/sendtelcode";
            case 5:
                return "/account/sendemailcode";
            case 6:
                return "/account/isbindtelpwd?";
            case 7:
                return "/account/regainedpwdbytel";
            case 8:
                return "/account/bindtel";
            case 9:
                return "/account/bindemailbypwd";
            case 10:
                return "/account/setpwdbytel";
            case 11:
                return "/account/setpwdbyemail";
            case 12:
                return "/account/setpwdbyoldpwd";
            case 13:
                return "/account/bindthirdparty";
            case 14:
                return "/account/bindemail";
            case 15:
                return "/account/verifyemail";
            case 16:
                return "/account/verifybindtel?";
            case 17:
                return "/account/verifybindemail?";
            case 18:
                return "/account/regainedpwdbyemail";
            case 19:
                return "https://api.weixin.qq.com/sns/oauth2/access_token";
            case 20:
                return "/loginV2/m_confirm";
            case 21 /*21*/:
                return "/loginV2/three_confirm";
            case 22:
                return "/login/again_register";
            case 23 /*23*/:
                return "";
            case 24 /*24*/:
                return "/latest/" + ".json";
            case 25 /*25*/:
                return "";
            case 26:
                return "/subject/" + ".json";
            case 28:
                return "/recommend/batchUpdate";
            case 29:
                return "/classify/filter.json";
            case 30 /*30*/:
                return "/classify/" + ".json";
            case 31:
                return "/rank/type_filter.json";
            case 32:
                return "/rank/" + ".json";
            case 33:
                return "/subject/category.json";
            case 34:
                return "/subject/" + ".json";
            case 35:
                return "/comment/" + ".json";
            case 36:
                return "/common/report";
            case 37 /*37*/:
                return "/comment/add";
            case 38/*38*/:
                return "/comment/agree";
            case 39 /*39*/:
                return "/comment/total/" + ".json";
            case 40/*40*/:
                return "/comment/owner/" + ".json";
            case 41/*41*/:
                return "/comment/detail/" + ".json";
            case 42 /*42*/:
                return "https://imgzip.dmzj.com/" + ".zip";
            case 43 /*43*/:
                return "/article/category.json";
            case 443 /*44*/:
                return "/v3/article/recommend/header.json";
            case 45 /*45*/:
                return "/v3/article/list/" + ".json";
            case 46 /*46*/:
                return "/article/mood/";
            case 47 /*47*/:
                return "https://interface.dmzj.com/api/news/subscribe/add";
            case 48 /*48*/:
                return "https://interface.dmzj.com/api/news/subscribe/check";
            case 49:
                return "/v3/article/total/" + ".json";
            case 50:
                return "https://interface.dmzj.com/api/news/subscribe/del";
            case 51:
                return "https://interface.dmzj.com/api/news/getSubscribe";
            case 52:
                return "/message/list/" + ".json";
            case 53:
                return "/viewPoint/" + ".json";
            case 54:
                return "/viewPoint/addv2";
            case 55:
                return "/viewPoint/praise";
            case 56:
                return "/hotView/" + ".json";
            case 57:
                return "/novel/recentUpdate/" + ".json";
            case 58:
                return "/novel/recommend.json";
            case 59:
                return "/1/category.json";
            case 60 /*60*/:
                return "/novel/filter.json";
            case 61:
                return "/novel/" + ".json";
            case 62:
                return "/novel/" + ".json";
            case 63:
                return "https://sacg.dmzj.com/lnovelsum/hit.php?";
            case 64:
                return "/novel/chapter/" + ".json";
            case 65:
                return "/novel/tag.json";
            case 66:
                return "/novel/rank/" + ".json";
            case 67:
                return "/novel/download/" + ".txt";
            case 68:
                return "/novel/collection/" + ".json";
            case 69:
                return "/novel/collection/" + ".json";
            case 70:
                return "https://api.dmzj.com/dynamic/comicversion/android";
            case 71:
                return "/bookList/" + ".json";
            case 72:
                return "/bookList/collection/" + ".json";
            case 73:
                return "/v3/subscribe/" + ".json";
            case 74:
                return "/UCenter/comics/";
            case 75:
                return "/UCenter/comicsv2/";
            case 76:
                return "/UCenter/author/" + ".json";
            case 77:
                return "/UCenter/subscribe";
            case 78:
                return "/bookList/collection/" + ".json";
            case 79:
                return "/subscribe/add";
            case 80:
                return "/subscribe/cancel";
            case 81:
                return "/subscribe/";
            case 82:
                return "/subscribe/read";
            case 83:
                String str2 = "/v3/comic/related/" + str + ".json";
                if ("".equals(str)) {
                    //b.c().getApi((m) new m("URLPathMaker.getUrlWithInnerParam").getApi("url", str2));
                }
                return str2;
            case 84:
                return "/chapter/" + ".json";
            case 85:
                return "/v3/2/category.json";
            case 86:
                return "/search/fuzzy/" + ".json";
            case 87:
                return "/search/show/" + ".json";
            case 89:
                return "/old/comment/" + ".json";
            case 90:
                return "/old/comment/detail/" + ".json";
            case 91:
                return "/old/comment/total/" + ".json";
            case 92:
                return "/old/comment/add";
            case 93:
                return "/old/comment/agree";
            case 94:
                return "https://v3comment.dmzj.com/v1/";
            case 95:
                return "/device/add";
            case 96:
                return "/device/building";
            case 97:
                return "/device/cancel";
            case 98:
                return "/account/cookie";
            case 99:
                return "/v3/old/comment/owner/" + ".json";
            case 100:
                return "https://api.dmzj.com/dynamic/comichitsv2";
            case 101 /*101*/:
                return "/device/push";
            case 102 /*102*/:
                return "https://api.dmzj.com/dynamic/activity";
            case 103 /*103*/:
                return "/account/update";
            case 104 /*104*/:
                return "/account/modifyname";
            case 105 /*105*/:
                return "https://interface.dmzj.com/api/record/getRe";
            case 106 /*106*/:
                return "https://interface.dmzj.com/api/getReInfo/";
            case 107 /*107*/:
                return "https://interface.dmzj.com/api/record/delRecords";
            case 108 /*108*/:
                return "https://v3comment.dmzj.com/v1/";
            case 109 /*109*/:
                return "/msg/works/";
            case 110 /*110*/:
                return "/msg/worksComment/";
            case 111 /*111*/:
                return "/comment2/" + ".json";
            case 112 /*112*/:
                return "https://v3comment.dmzj.com/v1/";
            case 113 /*113*/:
                return "/comment2/getTopComment/" + ".json";
            case 114:
                return "/msg/replyMe/";
            case 115:
                return "/msg/msgList/";
            case 116:
                return "/msg/chat/";
            case 117:
                return "/msg/sendMessage/";
            case 118:
                return "/msg/delMessage/";
            case 119:
                return "/msg/msgStatuChange/";
            case 120/*120*/:
                return "/msg/changeReply/";
            case 121:
                return "/msg/unread/";
            case 122:
                return "/msg/msgShield/";
            case 123:
                return "/msg/delShield/";
            case 124:
                return "/comment2/commentReport";
            case 125:
                return "/msg/shieldList/";
            case 126:
                return "/comment2/gagcheckv2/" + ".json";
            case 127 /*127*/:
                return "/game_c/game/" + ".json";
            case 128:
                return "/game_c.json";
            case 129:
                return "/game_c/game/detail/" + ".json";
            case 130 /*130*/:
                return "/game_c/gift/" + ".json";
            case 131:
                return "/game_c/gift/hasgift/" + ".json";
            case 132:
                return "/game_c/gift/detail/" + ".json";
            case 133:
                return "/game_c/gift/hascode/" + ".json";
            case 134:
                return "/game_c/gift/getcode/";
            case 135:
                return "/game_c/game/downcount";
            default:
                return str;
        }
    }

    static boolean getApi(String str) {
        return TextUtils.isEmpty(str) || str.contains("https://v3api.dmzj.com/comment2/gagcheckv2/") || str.contains("https://v3api.dmzj.com/comment2/getTopComment/") || str.contains("https://v3api.dmzj.com/comment2/") || str.contains("https://v3api.dmzj.com/comment/owner/") || str.contains("https://v3api.dmzj.com/comment/detail/") || str.contains("https://v3api.dmzj.com/v3/old/comment/owner/") || str.contains("https://v3api.dmzj.com/msg/worksComment/") || str.contains("https://v3comment.dmzj.com/v1/0/latest/") || str.contains("https://v3comment.dmzj.com/v1/1/latest/") || str.contains("https://v3comment.dmzj.com/v1/2/latest/") || str.contains("https://v3comment.dmzj.com/v1/3/latest/") || str.contains("https://v3comment.dmzj.com/v1/4/latest/") || str.contains("https://v3comment.dmzj.com/v1/5/latest/") || str.contains("https://v3comment.dmzj.com/v1/6/latest/") || str.contains("https://v3comment.dmzj.com/v1/7/latest/") || str.contains("https://v3comment.dmzj.com/v1/8/latest/") || str.contains("https://v3comment.dmzj.com/v1/9/latest/") || str.contains("https://v3comment.dmzj.com/v1/10/latest/");
    }

    @GET("/v3/recommend.json" + BaseLine)
    Call<ArrayList<ComicComm>> getRecommend();

    @GET("/0/category.json" + BaseLine)
    Call<ResponseBody> getCategory();

    @GET("/comic/{comicID}.json" + BaseLine)
    Call<ComicHomeComicChapterList> getComic(@Path("comicID") String mComicID);

    @GET("/chapter/{bookId}/{chapterId}.json" + BaseLine)
    Call<ChapterList> getComic(@Path("bookId") String bookId, @Path("chapterId") String chapterId);

    //https://images.dmzj.com/img/webpic/19/1003821191536309628.jpg

    /**
     * 热门连载刷新获取
     *
     * @param time 时间戳
     * @return
     */
    @GET("/recommend/batchUpdate" + BaseLine + "&category_id=54")
    Call<HotComic> getComicByHot(@Query("timestamp") int time);

    /**
     * 获取类别漫画点进去后的详细分类
     *
     * @return
     */
    @GET("/classify/filter.json" + BaseLine)
    Call<ResponseBody> getCategoryComic();

    /**
     * 获取类别漫画点进去后的默认所有漫画
     *
     * @return
     */
    @GET("/classify/{categoryType}/{a}/{page}.json" + BaseLine)
    Call<List<ComicHome_CategoryComic>> getCategoryComicAll(@Path("categoryType") String categoryType, @Path("a") String a, @Path("page") int page);

    @GET("/search/hot/0.json" + BaseLine + "&timestamp={time}")
    Call<ResponseBody> getHotSearchKey(@Path("time") long 时间戳);
}
