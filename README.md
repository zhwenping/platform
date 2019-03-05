# 一：项目框架的使用
  ## 1：南京局质量检测项目
  ### 项目简介
  >>主题颜色蓝色，使用okgo网络框架，Glide图片加载框架
  ### 项目框架的使用 
  >>implementation 'com.github.zhwenping:platform:2.0.0'  
# 二：项目框架的使用 
 ## 1：基类（BaseActivity）的使用
 ### 1）继承方法
 ### 2）常用函数
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
     @OnClick(R.id.tv_content)
     public void onViewClicked() {
    }
   ### 控件释放
     @Override
     protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }
 
      
 
 
