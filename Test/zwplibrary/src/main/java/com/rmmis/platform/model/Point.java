package com.rmmis.platform.model;

/**
 * Created by Edianzu on 2018/10/17.
 * 手势密码
 */
    public class Point {

        //正常状态
        public static int STATE_NORMAL = 0;
        //选中状态
        public static int STATE_PRESSED = 1;
        //错误状态
        public static int STATE_ERROR = 2;

        public float x, y;

        public int index = 0, status = 0;

        public Point() {

        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
