# 一：项目框架的使用
  ## 1：南京局质量检测项目
  ### 项目简介
  >>主题颜色蓝色，使用okgo网络框架，Glide图片加载框架
  ### 项目框架的使用 
  >>implementation 'com.github.zhwenping:platform:2.0.0'  
# 二：项目框架的介绍
 ## 1：基类（BaseActivity）的使用
 ### 1）继承方法
 #### 获取中间内容显示区
 >>protected abstract int getContentView();
  #### 初始化数据
  >> protected abstract void initData();
  #### 设置监听事件
  >> protected  void setListener(){};
  #### 点击右侧按钮
  >> protected void onClickRight() {}
  ####  获取自定义标题栏 (如果子类复写并返回不等于0的布局文件，将会覆盖默认标题;返回0 将会采用默认标题)
  >>  protected int getTitlebarResId() { return 0;}
    
 ### 2）常用函数
 ###### void setLeftBtnVisible(Boolean visible) true:显示左侧按钮， false：不显示左侧按钮
 ###### void setRightBtnVisible(Boolean visible) true:显示右侧按钮， false：不显示右侧按钮
 ###### void setTitle 设置中间标题
 ###### void setRtTitle 设置右侧标题
 ###### View getTitleBar() 获取标题栏控件

 ## 2：网络请求 
 ### 1）网络请求主题
      Map map = new HashMap();
      map.put("userId", userId);
      map.put("page", page);
      map.put("number", pageSize);

        OkGoUtil.post(mContext,Constant.GetJpushListByUserid, "GetJpushListByUserid", map, new DialogCallback<String>(getActivity())         {
            @Override
            public void onSuccess(Response<String> response) {

                try {
                    
                } catch (Exception e) {
                    ToastUtil.showToast(mContext,e.getMessage());
                    LogUtil.showLog(e.getMessage());
                }


            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);

            }
        });
   ### 2）网络请求释放资源
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkGoUtil.cancelTag(this);
   
    }

 ## 3：图片加载
 ### loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv)：加载网络图片 
 ### loadImage(Context context, String url, ImageView iv)：加载网络图片
 ### loadImage(Context context, final File file, final ImageView imageView)：加载本地文件
 ### loadImage(Context context, final int resourceId, final ImageView imageView)：加载资源文件下的文件
 
 ## 4：控件注解
   ### 控件注册
   >> ButterKnife.bind(this);
   ### 控件使用
   #### 定义控件
      @Bind(R.id.tv_title)
      TextView tvTitle;
      @Bind(R.id.tv_time)
      TextView tvTime;
   #### 点击事件
     @OnClick(R.id.tv_title,R.id.tv_time)
     public void onViewClicked() {
    }
   ### 控件释放
     @Override
     protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }
 
 # 三：项目框架的变更
 ## 1. 2.0.0 项目的初始版本 
 # 四：规范
 >> 1: 南京质量检测项目
 
 
