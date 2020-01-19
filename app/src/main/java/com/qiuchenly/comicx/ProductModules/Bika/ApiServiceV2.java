package com.qiuchenly.comicx.ProductModules.Bika;

import com.qiuchenly.comicx.ProductModules.Bika.requests.*;
import com.qiuchenly.comicx.ProductModules.Bika.responses.*;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.AnnouncementsResponse.AnnouncementsResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ApplicationsResponse.ApplicationsResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ComicPageResponse.ComicPagesResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.CommentsResponse.CommentsResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.GameDetailResponse.GameDetailResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.GameListResponse.GameListResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.PicaAppsResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.PostCommentResponse.PostCommentResponse;
import com.qiuchenly.comicx.ProductModules.Bika.responses.DataClass.ProfileCommentsResponse.ProfileCommentsResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiServiceV2 {
    @POST("utils/adjust-exp")
    Call<RegisterResponse> adjustExp(@Header("authorization") String str, @Body AdjustExpBody adjustExpBody);

    @POST("utils/block-user")
    Call<GeneralResponse> blockUser(@Header("authorization") String str, @Body UserIdBody userIdBody);

    @POST("comics/{comicId}/favourite")
    Call<GeneralResponse<ActionResponse>> bookmarkComicWithId(@Header("authorization") String str, @Path("comicId") String str2);

    @POST("auth/forgot-password")
    Call<RegisterResponse> forgotPassword(@Body ForgotPasswordBody forgotPasswordBody);

    @GET("announcements")
    Call<GeneralResponse<AnnouncementsResponse>> getAnnouncements(@Header("authorization") String str, @Query("page") int i);

    @GET("applications?platform=android")
    Call<GeneralResponse<ApplicationsResponse>> getApplications(@Header("authorization") String str, @Query("page") int i);

    @GET("banners")
    Call<GeneralResponse<BannersResponse>> getBanners(@Header("authorization") String str);

    @GET("categories")
    Call<GeneralResponse<CategoryResponse>> getCategories(@Header("authorization") String str);

    @GET("chat")
    Call<GeneralResponse<ChatroomListResponse>> getChatroomList(@Header("authorization") String str);

    @GET("comics/{comicId}/eps")
    Call<GeneralResponse<ComicEpisodeResponse>> getComicEpisode(@Header("authorization") String str, @Path("comicId") String str2, @Query("page") int i);

    @GET("comics")
    Call<GeneralResponse<ComicListResponse>> getComicList(@Header("authorization") String authorization, @Query("page") int page, @Query("c") String c, @Query("t") String t, @Query("a") String a, @Query("f") String f, @Query("s") String s, @Query("ct") String ct, @Query("ca") String ca);

    @GET("comics/search")
    Call<GeneralResponse<ComicListResponse>> getComicListWithSearchKey(@Header("authorization") String str, @Query("page") int i, @Query("q") String str2);

    @GET("comics/{comicId}")
    Call<GeneralResponse<ComicDetailResponse>> getComicWithId(@Header("authorization") String str, @Path("comicId") String str2);

    @GET("comics/{comicId}/comments")
    Call<GeneralResponse<CommentsResponse>> getCommentsWithComicId(@Header("authorization") String str, @Path("comicId") String str2, @Query("page") int i);

    @GET("comments/{commentId}/childrens")
    Call<GeneralResponse<CommentsResponse>> getCommentsWithCommentId(@Header("authorization") String str, @Path("commentId") String str2, @Query("page") int i);

    @GET("games/{gameId}/comments")
    Call<GeneralResponse<CommentsResponse>> getCommentsWithGameId(@Header("authorization") String str, @Path("gameId") String str2, @Query("page") int i);

    @GET("users/favourite")
    Call<GeneralResponse<ComicListResponse>> getFavourite(@Header("authorization") String str, @Query("page") int i);

    @GET("games/{gameId}")
    Call<GeneralResponse<GameDetailResponse>> getGameDetail(@Header("authorization") String str, @Path("gameId") String str2);

    @GET("games")
    Call<GeneralResponse<GameListResponse>> getGameList(@Header("authorization") String str, @Query("page") int i);

    @GET("init?platform=android")
    Call<GeneralResponse<InitialResponse>> getInit(@Header("authorization") String str);

    @GET("keywords")
    Call<GeneralResponse<KeywordsResponse>> getKeywords(@Header("authorization") String str);

    @GET("comics/knight-leaderboard")
    Call<GeneralResponse<LeaderboardKnightResponse>> getKnightLeaderboard(@Header("authorization") String str);

    @GET("comics/leaderboard")
    Call<GeneralResponse<LeaderboardResponse>> getLeaderboard(@Header("authorization") String str, @Query("tt") String str2, @Query("ct") String str3);

    @GET("eps/{epsId}/download")
    Call<GeneralResponse<ComicPagesResponse>> getPagesWithEpisodeId(@Header("authorization") String str, @Path("epsId") String str2);

    @GET("eps/{epsId}/pages")
    Call<GeneralResponse<ComicPagesResponse>> getPagesWithEpisodeId(@Header("authorization") String str, @Path("epsId") String str2, @Query("page") int i);

    @GET("comics/{comicId}/order/{order}/pages")
    Call<GeneralResponse<ComicPagesResponse>> getPagesWithOrder(@Header("authorization") String str, @Path("comicId") String str2, @Path("order") int i, @Query("page") int i2);

    @GET("pica-apps")
    Call<GeneralResponse<PicaAppsResponse>> getPicaApps(@Header("authorization") String str);

    @GET("users/my-comments")
    Call<GeneralResponse<ProfileCommentsResponse>> getProfileComments(@Header("authorization") String str, @Query("page") int i);

    @GET("comics/random")
    Call<GeneralResponse<ComicRandomListResponse>> getRandomComicList(@Header("authorization") String str);

    @GET("tags")
    Call<GeneralResponse<TagListResponse>> getTags(@Header("authorization") String str);

    @GET("users/profile")
    Call<GeneralResponse<UserProfileResponse>> getUserProfile(@Header("authorization") String str);

    @GET("users/{userId}/profile")
    Call<GeneralResponse<UserProfileResponse>> getUserProfileWithUserId(@Header("authorization") String str, @Path("userId") String str2);

    @GET("init")
    Call<WakaInitResponse> getWakaInit();

    @POST("comments/{commentId}/hide")
    Call<GeneralResponse<MessageResponse>> hideCommentWithCommentId(@Header("authorization") String str, @Path("commentId") String str2);

    @POST("comics/{comicId}/like")
    Call<GeneralResponse<ActionResponse>> likeComicWithId(@Header("authorization") String str, @Path("comicId") String str2);

    @POST("comments/{commentId}/like")
    Call<GeneralResponse<ActionResponse>> likeCommentWithId(@Header("authorization") String str, @Path("commentId") String str2);

    @POST("games/{gameId}/like")
    Call<GeneralResponse<ActionResponse>> likeGameWithId(@Header("authorization") String str, @Path("gameId") String str2);

    @POST("comments/{commentId}/top")
    Call<GeneralResponse<CommentPostToTopResponse>> postCommentToTheTop(@Header("authorization") String str, @Path("commentId") String str2);

    @POST("comics/{comicId}/comments")
    Call<GeneralResponse<PostCommentResponse>> postCommentWithComicId(@Header("authorization") String str, @Path("comicId") String str2, @Body CommentBody commentBody);

    @POST("comments/{commentId}")
    Call<GeneralResponse<PostCommentResponse>> postCommentWithCommentId(@Header("authorization") String str, @Path("commentId") String str2, @Body CommentBody commentBody);

    @POST("games/{gameId}/comments")
    Call<GeneralResponse<PostCommentResponse>> postCommentWithGameId(@Header("authorization") String str, @Path("gameId") String str2, @Body CommentBody commentBody);

    @POST("users/{userId}/dirty")
    Call<GeneralResponse<UserProfileDirtyResponse>> postDirty(@Header("authorization") String str, @Path("userId") String str2);

    @POST("users/punch-in")
    Call<GeneralResponse<PunchInResponse>> punchIn(@Header("authorization") String str);

    @PUT("users/avatar")
    Call<GeneralResponse<PutAvatarResponse>> putUserAvatar(@Header("authorization") String str, @Body AvatarBody avatarBody);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterBody registerBody);

    @POST("utils/remove-comment")
    Call<GeneralResponse> removeAllComment(@Header("authorization") String str, @Body UserIdBody userIdBody);

    @POST("auth/resend-activation")
    Call<RegisterResponse> resendActivation(@Body ForgotPasswordBody forgotPasswordBody);

    @POST("auth/sign-in")
    Call<GeneralResponse<SignInResponse>> signIn(@Body SignInBody signInBody);

    @PUT("users/profile")
    Call<RegisterResponse> updateProfile(@Header("authorization") String str, @Body UpdateProfileBody updateProfileBody);

    @PUT("users/password")
    Call<RegisterResponse> updateUserPassword(@Header("authorization") String str, @Body ChangePasswordBody changePasswordBody);

    @PUT("users/{userId}/title")
    Call<RegisterResponse> updateUserTitle(@Header("authorization") String str, @Path("userId") String str2, @Body UpdateUserTitleBody updateUserTitleBody);

    @POST("games/{gameId}/like")
    Call<GeneralResponse<ActionResponse>> A(@Header("authorization") String str, @Path("gameId") String str2);

//    @POST("auth/forgot-password")
//    Call<GeneralResponse<ForgotPasswordResponse>> a(@Body ForgotPasswordBody forgotPasswordBody);
//    @POST("auth/reset-password")
//    Call<GeneralResponse<PasswordResponse>> a(@Body ResetPasswordBody resetPasswordBody);

//    @POST("comics/advanced-search")
//    Call<GeneralResponse<ComicListResponse>> a(@Header("authorization") String str, @Query("page") int i, @Body SortingBody sortingBody);
//
//    @GET("comics")
//    Call<GeneralResponse<ComicListResponse>> a(@Header("authorization") String str, @Query("page") int i, @Query("c") String str2, @Query("t") String str3, @Query("a") String str4, @Query("f") String str5, @Query("s") String str6, @Query("ct") String str7, @Query("ca") String str8);

//    @POST("utils/adjust-exp")
//    Call<RegisterResponse> a(@Header("authorization") String str, @Body AdjustExpBody adjustExpBody);
//
//    @PUT("users/avatar")
//    Call<GeneralResponse<PutAvatarResponse>> a(@Header("authorization") String str, @Body AvatarBody avatarBody);
//
//    @PUT("users/password")
//    Call<RegisterResponse> a(@Header("authorization") String str, @Body ChangePasswordBody changePasswordBody);
//
//    @PUT("users/update-id")
//    Call<GeneralResponse> a(@Header("authorization") String str, @Body UpdatePicaIdBody updatePicaIdBody);
//
//    @PUT("users/profile")
//    Call<RegisterResponse> a(@Header("authorization") String str, @Body UpdateProfileBody updateProfileBody);
//
//    @PUT("users/update-qa")
//    Call<GeneralResponse> a(@Header("authorization") String str, @Body UpdateQandABody updateQandABody);
//
//    @POST("utils/remove-comment")
//    Call<GeneralResponse> a(@Header("authorization") String str, @Body UserIdBody userIdBody);
//
//    @GET("users/favourite")
//    Call<GeneralResponse<ComicListResponse>> a(@Header("authorization") String str, @Query("s") String str2, @Query("page") int i);
//
//    @GET("comics/{comicId}/order/{order}/pages")
//    Call<GeneralResponse<ComicPagesResponse>> a(@Header("authorization") String str, @Path("comicId") String str2, @Path("order") int i, @Query("page") int i2);
//
//    @POST("comics/{comicId}/comments")
//    Call<GeneralResponse<PostCommentResponse>> a(@Header("authorization") String str, @Path("comicId") String str2, @Body CommentBody commentBody);
//
//    @PUT("users/{userId}/title")
//    Call<RegisterResponse> a(@Header("authorization") String str, @Path("userId") String str2, @Body UpdateUserTitleBody updateUserTitleBody);
//
//    @GET("comics/leaderboard")
//    Call<GeneralResponse<LeaderboardResponse>> a(@Header("authorization") String str, @Query("tt") String str2, @Query("ct") String str3);
//
//    @GET("init?platform=android")
//    Call<GeneralResponse<InitialResponse>> ak(@Header("authorization") String str);
//
//    @GET("categories")
//    Call<GeneralResponse<CategoryResponse>> al(@Header("authorization") String str);
//
//    @GET("users/profile")
//    Call<GeneralResponse<UserProfileResponse>> am(@Header("authorization") String str);
//
//    @POST("users/punch-in")
//    Call<GeneralResponse<PunchInResponse>> an(@Header("authorization") String str);
//
//    @GET("comics/random")
//    Call<GeneralResponse<ComicRandomListResponse>> ao(@Header("authorization") String str);
//
//    @GET("comics/knight-leaderboard")
//    Call<GeneralResponse<LeaderboardKnightResponse>> ap(@Header("authorization") String str);

    @GET("collections")
    Call<GeneralResponse<CollectionsResponse>> getCollections(@Header("authorization") String authorization);

//    @GET("keywords")
//    Call<GeneralResponse<KeywordsResponse>> ar(@Header("authorization") String str);
//
//    @GET("banners")
//    Call<GeneralResponse<BannersResponse>> as(@Header("authorization") String str);
//
//    @GET("chat")
//    Call<GeneralResponse<ChatroomListResponse>> at(@Header("authorization") String str);
//
//    @GET("pica-apps")
//    Call<GeneralResponse<PicaAppsResponse>> au(@Header("authorization") String str);
//
//    @GET("applications?platform=android")
//    Call<GeneralResponse<ApplicationsResponse>> b(@Header("authorization") String str, @Query("page") int i);
//
//    @POST("utils/block-user")
//    Call<GeneralResponse> b(@Header("authorization") String str, @Body UserIdBody userIdBody);
//
//    @GET("comics/{comicId}/eps")
//    Call<GeneralResponse<ComicEpisodeResponse>> b(@Header("authorization") String str, @Path("comicId") String str2, @Query("page") int i);
//
//    @POST("comments/{commentId}")
//    Call<GeneralResponse<PostCommentResponse>> b(@Header("authorization") String str, @Path("commentId") String str2, @Body CommentBody commentBody);
//
//    @GET("users/my-comments")
//    Call<GeneralResponse<ProfileCommentsResponse>> c(@Header("authorization") String str, @Query("page") int i);
//
//    @GET("comics/{comicId}/comments")
//    Call<GeneralResponse<CommentsResponse>> c(@Header("authorization") String str, @Path("comicId") String str2, @Query("page") int i);
//
//    @POST("games/{gameId}/comments")
//    Call<GeneralResponse<PostCommentResponse>> c(@Header("authorization") String str, @Path("gameId") String str2, @Body CommentBody commentBody);
//
//    @GET("users/notifications")
//    Call<GeneralResponse<NotificationsResponse>> d(@Header("authorization") String str, @Query("page") int i);
//
//    @GET("comments/{commentId}/childrens")
//    Call<GeneralResponse<CommentsResponse>> d(@Header("authorization") String str, @Path("commentId") String str2, @Query("page") int i);
//
//    @GET("init")
//    Call<WakaInitResponse> dM();
//
//    @GET("games")
//    Call<GeneralResponse<GameListResponse>> e(@Header("authorization") String str, @Query("page") int i);
//
//    @GET("eps/{epsId}/pages")
//    Call<GeneralResponse<ComicPagesResponse>> e(@Header("authorization") String str, @Path("epsId") String str2, @Query("page") int i);
//
//    @GET("announcements")
//    Call<GeneralResponse<AnnouncementsResponse>> f(@Header("authorization") String str, @Query("page") int i);
//
//    @GET("games/{gameId}/comments")
//    Call<GeneralResponse<CommentsResponse>> f(@Header("authorization") String str, @Path("gameId") String str2, @Query("page") int i);
//
//    @POST("users/{userId}/dirty")
//    Call<GeneralResponse<UserProfileDirtyResponse>> p(@Header("authorization") String str, @Path("userId") String str2);
//
//    @GET("users/{userId}/profile")
//    Call<GeneralResponse<UserProfileResponse>> q(@Header("authorization") String str, @Path("userId") String str2);
//
//    @GET("comics/{comicId}")
//    Call<GeneralResponse<ComicDetailResponse>> r(@Header("authorization") String str, @Path("comicId") String str2);
//
//    @POST("comics/{comicId}/like")
//    Call<GeneralResponse<ActionResponse>> s(@Header("authorization") String str, @Path("comicId") String str2);
//
//    @POST("comics/{comicId}/favourite")
//    Call<GeneralResponse<ActionResponse>> t(@Header("authorization") String str, @Path("comicId") String str2);
//
//    @GET("comics/{comicId}/recommendation")
//    Call<GeneralResponse<ComicRandomListResponse>> u(@Header("authorization") String str, @Path("comicId") String str2);
//
//    @POST("comments/{commentId}/like")
//    Call<GeneralResponse<ActionResponse>> v(@Header("authorization") String str, @Path("commentId") String str2);
//
//    @POST("comments/{commentId}/hide")
//    Call<GeneralResponse<MessageResponse>> w(@Header("authorization") String str, @Path("commentId") String str2);
//
//    @POST("comments/{commentId}/report")
//    Call<GeneralResponse<MessageResponse>> x(@Header("authorization") String str, @Path("commentId") String str2);
//
//    @GET("games/{gameId}")
//    Call<GeneralResponse<GameDetailResponse>> z(@Header("authorization") String str, @Path("gameId") String str2);
}