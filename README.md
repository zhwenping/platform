# 一：项目框架的使用
  ## 1：南京局质量检测项目
  ### 项目简介
  >>主题颜色蓝色，使用okgo网络框架，Glide图片加载框架
  ### 项目框架的使用 
  >>implementation 'com.github.zhwenping:platform:2.0.0'  
# 二：项目框架的使用      
 ## 1：网络请求 
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

 ## 1：图片加载
 
 
