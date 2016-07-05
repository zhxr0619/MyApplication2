//package app.univs.cn.myapplication;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.tandong.sa.animation.ViewHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class NavigationActivity extends Activity {
//
//    private MyViewPager viewPager;
//
//    private List<ImageView> imageViewList=new ArrayList<ImageView>();
//
//    private int[] ivIds={R.mipmap.yin_dao1,R.mipmap.yin_dao2,R.mipmap.yin_dao3};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_navigation);
//
//        init();
//    }
//
//    private void init(){
//        viewPager=(MyViewPager)findViewById(R.id.viewpager);
//
//        for (int imgId:ivIds){
//            ImageView iv=new ImageView(getApplicationContext());
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            iv.setImageResource(imgId);
//            imageViewList.add(iv);
//        }
//
//        viewPager.setAdapter(new MyAdapter());
//
//        viewPager.setPageTransformer(true,new RotateDownPageTransformer());
//    }
//
//    class MyAdapter extends PagerAdapter{
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            container.addView(imageViewList.get(position));
//            return imageViewList.get(position);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(imageViewList.get(position));
//        }
//
//        @Override
//        public int getCount() {
//            return imageViewList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view==object;
//        }
//    }
//
//    /*class DepthPageTransformer implements ViewPager.PageTransformer{
//            private static final float MIN_SCALE = 0.75f;
//
//            public void transformPage(View view, float position) {
//                int pageWidth = view.getWidth();
//
//                if (position < -1) { // [-Infinity,-1)
//                    // This page is way off-screen to the left.
//                    view.setAlpha(0);
//
//                } else if (position <= 0) { // [-1,0]
//                    // Use the default slide transition when moving to the left page
//                    view.setAlpha(1);
//                    view.setTranslationX(0);
//                    view.setScaleX(1);
//                    view.setScaleY(1);
//
//                } else if (position <= 1) { // (0,1]
//                    // Fade the page out.
//                    view.setAlpha(1 - position);
//
//                    // Counteract the default slide transition
//                    view.setTranslationX(pageWidth * -position);
//
//                    // Scale the page down (between MIN_SCALE and 1)
//                    float scaleFactor = MIN_SCALE
//                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
//                    view.setScaleX(scaleFactor);
//                    view.setScaleY(scaleFactor);
//
//                } else { // (1,+Infinity]
//                    // This page is way off-screen to the right.
//                    view.setAlpha(0);
//                }
//            }
//    }*/
//
//    public class ZoomOutPageTransformer implements MyViewPager.PageTransformer
//    {
//        private static final float MIN_SCALE = 0.85f;
//        private static final float MIN_ALPHA = 0.5f;
//
//        @SuppressLint("NewApi")
//        public void transformPage(View view, float position)
//        {
//            int pageWidth = view.getWidth();
//            int pageHeight = view.getHeight();
//
//            Log.e("TAG", view + " , " + position + "");
//
//            if (position < -1)
//            { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                view.setAlpha(0);
//
//            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
//            { // [-1,1]
//                // Modify the default slide transition to shrink the page as well
//                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
//                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
//                if (position < 0)
//                {
//                    view.setTranslationX(horzMargin - vertMargin / 2);
//                } else
//                {
//                    view.setTranslationX(-horzMargin + vertMargin / 2);
//                }
//
//                // Scale the page down (between MIN_SCALE and 1)
//                view.setScaleX(scaleFactor);
//                view.setScaleY(scaleFactor);
//
//                // Fade the page relative to its size.
//                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
//                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
//
//            } else
//            { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                view.setAlpha(0);
//            }
//        }
//    }
//
//
//    public class DepthPageTransformer implements MyViewPager.PageTransformer
//    {
//        private static final float MIN_SCALE = 0.75f;
//
//        public void transformPage(View view, float position)
//        {
//            int pageWidth = view.getWidth();
//
//            if (position < -1)
//            { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                // view.setAlpha(0);
//                ViewHelper.setAlpha(view, 0);
//            } else if (position <= 0)// a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
//            { // [-1,0]
//                // Use the default slide transition when moving to the left page
//                // view.setAlpha(1);
//                ViewHelper.setAlpha(view, 1);
//                // view.setTranslationX(0);
//                ViewHelper.setTranslationX(view, 0);
//                // view.setScaleX(1);
//                ViewHelper.setScaleX(view, 1);
//                // view.setScaleY(1);
//                ViewHelper.setScaleY(view, 1);
//
//            } else if (position <= 1)
//            { // (0,1]
//                // Fade the page out.
//                // view.setAlpha(1 - position);
//                ViewHelper.setAlpha(view, 1 - position);
//
//                // Counteract the default slide transition
//                // view.setTranslationX(pageWidth * -position);
//                ViewHelper.setTranslationX(view, pageWidth * -position);
//
//                // Scale the page down (between MIN_SCALE and 1)
//                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
//                // view.setScaleX(scaleFactor);
//                ViewHelper.setScaleX(view, scaleFactor);
//                // view.setScaleY(1);
//                ViewHelper.setScaleY(view, scaleFactor);
//
//            } else
//            { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                // view.setAlpha(0);
//                ViewHelper.setAlpha(view, 1);
//            }
//        }
//    }
//
//
//
//    public class RotateDownPageTransformer implements MyViewPager.PageTransformer
//    {
//
//        private static final float ROT_MAX = 20.0f;
//        private float mRot;
//
//
//
//        public void transformPage(View view, float position)
//        {
//
//            Log.e("TAG", view + " , " + position + "");
//
//            if (position < -1)
//            { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                ViewHelper.setRotation(view, 0);
//
//            } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
//            { // [-1,1]
//                // Modify the default slide transition to shrink the page as well
//                if (position < 0)
//                {
//
//                    mRot = (ROT_MAX * position);
//                    ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
//                    ViewHelper.setPivotY(view, view.getMeasuredHeight());
//                    ViewHelper.setRotation(view, mRot);
//                } else
//                {
//
//                    mRot = (ROT_MAX * position);
//                    ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
//                    ViewHelper.setPivotY(view, view.getMeasuredHeight());
//                    ViewHelper.setRotation(view, mRot);
//                }
//
//                // Scale the page down (between MIN_SCALE and 1)
//
//                // Fade the page relative to its size.
//
//            } else
//            { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                ViewHelper.setRotation(view, 0);
//            }
//        }
//    }
//}
