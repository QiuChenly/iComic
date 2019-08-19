package com.qiuchenly.comicx.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.qiuchenly.comicx.Bean.*
import com.qiuchenly.comicx.Bean.RecommendItemType.TYPE.Companion.TYPE_BIKA
import com.qiuchenly.comicx.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_HOT
import com.qiuchenly.comicx.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_LASTUPDATE
import com.qiuchenly.comicx.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_NORMAL
import com.qiuchenly.comicx.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_SPEC_2
import com.qiuchenly.comicx.Bean.RecommendItemType.TYPE.Companion.TYPE_DONGMANZHIJIA_CATEGORY
import com.qiuchenly.comicx.Bean.RecommendItemType.TYPE.Companion.TYPE_TITLE
import com.qiuchenly.comicx.Bean.RecommendItemType.TYPE.Companion.TYPE_TOP
import com.qiuchenly.comicx.Core.ActivityKey.KEY_CATEGORY_JUMP
import com.qiuchenly.comicx.R
import com.qiuchenly.comicx.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicx.UI.activity.SearchResult
import com.qiuchenly.comicx.UI.view.ComicHomeContract
import com.qiuchenly.comicx.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*
import kotlinx.android.synthetic.main.item_rankview.view.*
import kotlinx.android.synthetic.main.item_recommend_normal.view.*
import kotlinx.android.synthetic.main.vpitem_top_ad.view.*
import java.lang.ref.WeakReference


class ComicHomeAdapter(var mBaseView: ComicHomeContract.View, private var mContext: WeakReference<Context?>) :
    BaseRecyclerAdapter<RecommendItemType>(), ComicHomeContract.DMZJ_Adapter {
    override fun addDMZJCategory(mComicCategory: ArrayList<ComicHomeCategory>) {
        addData(RecommendItemType().apply {
            this.title = "动漫之家全部分类"
            type = TYPE_TITLE
        })
        mComicCategory.forEach {
            addData(RecommendItemType().apply {
                type = TYPE_DONGMANZHIJIA_CATEGORY
                this.mItemData = Gson().toJson(it)
            })
        }
    }

    private var isInitForLoadMore = false

    //加载热门漫画数据
    fun addDMZJHot(mComicCategory: ArrayList<DataItem>) {
        if (!isInitForLoadMore) {
            addData(RecommendItemType().apply {
                this.title = "热门推荐漫画"
                type = TYPE_TITLE
            })
            isInitForLoadMore = true
        }
        mComicCategory.forEach {
            if (it.type == "") {
                it.type = "1"
                it.obj_id = it.id
            } //"猜你喜欢"这里会有一个bug,返回的数据type为空

            addData(RecommendItemType().apply {
                type = TYPE_DMZJ_HOT
                this.mItemData = Gson().toJson(it)
            })
        }
    }

    /**
     * 在mInitUI(param1,param2,param3)方法后被调用.先初始化Item数据再显示该Item
     */
    override fun onViewShowOrHide(position: Int, item: View, isShow: Boolean) {
        if (getItemViewType(position) == TYPE_TOP) {
            if (isShow) {
                mCarouselAdapter.startScroll()
            } else {
                mCarouselAdapter.aWaitScroll()
            }
        }
    }

    override fun canLoadMore(): Boolean {
        return true
    }

    override fun getViewType(position: Int): Int {
        return if (position < getRealSize()) position
        else super.getViewType(position)
    }

    override fun getItemLayout(viewType: Int): Int {
        return when (viewType) {
            TYPE_TOP -> R.layout.item_recommend_topview
            RecommendItemType.TYPE.TYPE_RANK -> R.layout.item_rankview
            TYPE_DMZJ_NORMAL,
            TYPE_DMZJ_LASTUPDATE,
            TYPE_DONGMANZHIJIA_CATEGORY,
            TYPE_DMZJ_HOT,//加载热门漫画
            TYPE_BIKA -> R.layout.item_foosize_newupdate
            TYPE_DMZJ_SPEC_2 -> R.layout.item_foosize_newupdate_2
            TYPE_TITLE -> R.layout.item_recommend_normal
            else -> R.layout.item_recommend_normal
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < getRealSize())
            getItemData(position).type
        else
            super.getItemViewType(position)
    }

    override fun onViewShow(item: View, data: RecommendItemType, position: Int, ViewType: Int) {
        mInitUI(item, data, position)
    }

    fun getSizeByItem(position: Int): Int {
        return when (getItemViewType(position)) {
            TYPE_BIKA,
            TYPE_DMZJ_NORMAL,
            TYPE_DMZJ_HOT,
            TYPE_DONGMANZHIJIA_CATEGORY,
            TYPE_DMZJ_LASTUPDATE -> 2
            TYPE_DMZJ_SPEC_2 -> 3
            else -> 6
        }
    }

    private var mCarouselAdapter = object : CarouselAdapter() {
        override fun onViewInitialization(mView: View, itemData: String?, position: Int) {
            with(mView) {
                tv_bookName.text = mTopTitles[position]
                CustomUtils.loadImageCircle(mView.context, mTopImages[position], vp_item_topad_cv, 15)
                this.setOnClickListener {
                    val itemData = mComicList?.get(0)?.data?.get(position)!!
                    val mFilterIntent = when (itemData["type"]) {
                        "7" -> {//动漫之家公告
                            Intent("android.intent.action.GET_DMZJ_URL").apply {
                                putExtras(Bundle().apply {
                                    //漫画基本信息 做跳转
                                    putString(KEY_CATEGORY_JUMP, itemData["url"])
                                })
                            }
                        }
                        "1" -> {//漫画
                            //将数据与普通漫画数据格式化一致,修复加载数据问题.
                            val mComicStringRealInfo = com.google.gson.Gson().toJson(itemData)
                            Intent("android.intent.action.ComicDetails").apply {
                                putExtras(Bundle().apply {
                                    //漫画基本信息 做跳转
                                    putString(KEY_CATEGORY_JUMP, com.google.gson.Gson().toJson(ComicInfoBean().apply {
                                        this.mComicType = ComicSource.DongManZhiJia
                                        this.mComicString = mComicStringRealInfo
                                    }))
                                })
                            }
                        }
                        else -> null
                    }
                    if (mFilterIntent != null) {
                        mContext.get()?.startActivity(mFilterIntent)
                    }
                }
            }
        }
    }

    private var sIsSetData = false
    @SuppressLint("SetTextI18n")
    private fun mInitUI(view: View, data: RecommendItemType?, position: Int) {
        when (data?.type) {
            /**
             * Banner栏数据
             */
            TYPE_TOP -> {
                if (!sIsSetData) {
                    mCarouselAdapter.setData(mTopTitles)
                    val mViewPager = view.findViewById<ViewPager>(R.id.vp_banner)
                    mViewPager.adapter = mCarouselAdapter
                    mCarouselAdapter.setVP(mViewPager)
                    sIsSetData = true
                }
            }
            /**
             * 暂时没用到
             */
            RecommendItemType.TYPE.TYPE_RANK -> {
                //RANK 点击
                with(view) {
                    tv_times.text = (java.util.Calendar.getInstance()
                        .get(java.util.Calendar.DAY_OF_MONTH)
                            ).toString()
                    CustomUtils.loadImage(view.context, "随机图片1", iv_privatefm_img_back, 55, 500)
                    CustomUtils.loadImage(view.context, "随机图片1", iv_day_img_back, 55, 500)
                    CustomUtils.loadImage(view.context, "随机图片1", iv_mix_img_back, 55, 500)
                    CustomUtils.loadImage(view.context, "随机图片1", iv_charts_img_back, 55, 500)
                    iv_day_img_click.setOnClickListener {
                        /*  startActivity(view.context,
                                  Intent(view.context, EveryDayRecommend::class.java),
                                  null)*/
                    }
                }
            }
            /**
             * 类别标题
             */
            TYPE_TITLE -> {
                //RANK 点击
                with(view) {
                    tv_listName.text = data.title
                    setOnClickListener(null)
                }
            }
            /**
             * 动漫之家/分类处理数据专属
             * ID对照表
             *  6 = 火热专题广告
             *  5 = 新漫周刊数据 与6一致
             *  8 = 大师级作者
             *  1 = 漫画
             */
            TYPE_DMZJ_NORMAL,
            TYPE_DMZJ_LASTUPDATE,
            TYPE_DMZJ_HOT,
            TYPE_DMZJ_SPEC_2 -> {
                var mImage = ""
                var mComicBookName = ""
                var mComicStatusOrAuthor = ""
                var mItemComicType: String //漫画
                var mComicStringRealInfo: String
                with(view) {
                    when (data.type) {
                        TYPE_DMZJ_LASTUPDATE -> {
                            val item = Gson().fromJson(data.mItemData, Map::class.java) as Map<String, String>
                            mImage = item["cover"] ?: ""
                            mItemComicType = "1"
                            mComicBookName = item["title"] ?: ""
                            mComicStatusOrAuthor = item["authors"] ?: ""
                            //将数据与普通漫画数据格式化一致,修复加载数据问题.
                            mComicStringRealInfo = Gson().toJson(DataItem().apply {
                                this.cover = item["cover"] ?: ""
                                this.obj_id = item["id"] ?: ""
                                this.sub_title = item["authors"] ?: ""
                                this.title = item["title"] ?: ""
                                this.status = item["status"] ?: ""
                            })
                        }
                        else -> {
                            val mItemData = Gson().fromJson(data.mItemData, DataItem::class.java)
                            mItemComicType = mItemData.type
                            mComicStringRealInfo = data.mItemData
                            mImage = mItemData.cover
                            mComicBookName = mItemData.title
                            mComicStatusOrAuthor =
                                if (mItemData.sub_title == "") mItemData.status
                                else mItemData.sub_title

                            foo_bookName_upNews.visibility =
                                when (mItemData.type) {
                                    //隐藏下半部分的栏
                                    "8", "6", "5" -> View.INVISIBLE
                                    else -> View.VISIBLE
                                }
                        }
                    }
                    CustomUtils.loadImage(view.context, mImage, foo_bookImg, 0, 500)
                    foo_bookName.text = mComicBookName
                    foo_bookName_upNews.text = mComicStatusOrAuthor

                    setOnClickListener {
                        //TODO 此处需要作进一步优化
                        val mIntent = when (mItemComicType) {
                            "1" -> {
                                Intent("android.intent.action.ComicDetails").apply {
                                    putExtra(KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                                        this.mComicType = ComicSource.DongManZhiJia
                                        this.mComicString = mComicStringRealInfo
                                    }))
                                }
                            }
                            "8", "6" -> {
                                null
                            }
                            "5" -> {
                                //周刊数据列表未处理
                                null
                            }
                            else -> null
                        }
                        if (mIntent != null) {
                            context.startActivity(mIntent)
                        }
                    }
                }
            }
            TYPE_DONGMANZHIJIA_CATEGORY -> {
                with(view) {
                    val mCate = Gson().fromJson(data.mItemData, ComicHomeCategory::class.java)
                    var mImageSrc = ""
                    var mCategoryName = ""
                    val mType = ComicSource.DongManZhiJia
                    mCategoryName = mCate.title
                    mImageSrc = mCate.cover


                    CustomUtils.loadImage(view.context, mImageSrc, foo_bookImg, 0, 500)
                    foo_bookName.text = mCategoryName
                    //for this type,unuseless
                    foo_bookName_upNews.visibility = View.GONE
                    setOnClickListener {

                        context.startActivity(Intent(context, SearchResult::class.java).apply {
                            putExtra(KEY_CATEGORY_JUMP, Gson().toJson(ComicCategoryBean().apply {
                                this.mCategoryName = mCategoryName
                                this.mComicType = mType
                                this.mData = data.mItemData
                            }
                            ))
                        }, null)
                    }
                }
            }
        }
    }

    //=====================  动漫之家数据处理  =====================
    private var mComicList: ArrayList<ComicComm>? = null
    private var mTopImages = arrayListOf<String>()
    private var mTopTitles = arrayListOf<String>()

    override fun addDMZJData(mComicList: ArrayList<ComicComm>) {
        setData(ArrayList())
        sIsSetData = false
        isInitForLoadMore = false
        mTopImages = arrayListOf()
        mTopTitles = arrayListOf()
        this.mComicList = mComicList
        for (item in mComicList) {
            if (item.category_id != "46")
                addData(RecommendItemType().apply {
                    this.title = item.title
                    type = TYPE_TITLE
                })

            when (item.category_id) {
                "46" -> {
                    //顶端首页数据
                    addData(RecommendItemType().apply {
                        this.title = item.title
                        type = TYPE_TOP
                    })
                    if (item.data != null) {
                        for (item2 in item.data!!) {
                            mTopImages.add(item2["cover"] ?: "")
                            mTopTitles.add(item2["title"] ?: "")
                        }
                    }
                }
                // 56 最新上架也包含在内
                else -> {
                    if (item.data != null) {
                        for (item2 in item.data!!) {
                            addData(RecommendItemType().apply {
                                this.title = item2["title"]
                                type = when (item.category_id) {
                                    "48", "53", "55" -> TYPE_DMZJ_SPEC_2
                                    "56" -> TYPE_DMZJ_LASTUPDATE
                                    else -> TYPE_DMZJ_NORMAL
                                }
                                this.mItemData = Gson().toJson(item2)
                            })
                        }
                    }
                }
            }
        }
    }
}