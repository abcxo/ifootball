package com.abcxo.android.ifootball.views.crosswalk;

/**
 * Created by shadow on 16/1/10.
 */
public class WebView {/*extends XWalkView {


    public WebView(Context context) {
        super(context);
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebView(Context context, Activity activity) {
        super(context, activity);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
    }

    public void loadUrl(String url) {
        load(url, null);
    }

    public void onResume() {
        resumeTimers();
        onShow();

    }


    public void onPause() {
        pauseTimers();
        onHide();
    }

    public void destroy() {
        super.onDestroy();
    }


    public boolean canGoBack() {
        if (hasEnteredFullscreen()) {
            leaveFullscreen();
            return true;
        }
        return getNavigationHistory().canGoBack();
    }

    public void goBack() {
        getNavigationHistory().navigate(
                XWalkNavigationHistory.Direction.BACKWARD, 1);
    }


    public void setWebViewClient(final WebViewClient client) {
        setResourceClient(new XWalkResourceClient(this) {
                              @Override
                              public void onLoadFinished(XWalkView view, String url) {
                                  super.onLoadFinished(view, url);
                                  client.onPageFinished((WebView) view, url);

                              }

                              @Override
                              public void onLoadStarted(XWalkView view, String url) {
                                  super.onLoadStarted(view, url);
                                  client.onPageStarted((WebView) view, url, null);
                              }

                              @Override
                              public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
                                  if (!url.equals(getUrl())) {
                                      return client.shouldOverrideUrlLoading((WebView) view, url);
                                  }
                                  return super.shouldOverrideUrlLoading(view, url);
                              }

                          }
        );


    }


    public void setWebChromeClient(final WebChromeClient client) {
        setUIClient(new XWalkUIClient(this) {
            @Override
            public void onReceivedTitle(XWalkView view, String title) {
                super.onReceivedTitle(view, title);
                client.onReceivedTitle((WebView) view, title);
            }

            @Override
            public void onReceivedIcon(XWalkView view, String url, Bitmap icon) {
                super.onReceivedIcon(view, url, icon);
                client.onReceivedIcon((WebView) view, icon);
            }

            @Override
            public boolean shouldOverrideKeyEvent(XWalkView view, KeyEvent event) {
                ((Activity) view.getContext()).onBackPressed();
                return true;
            }

            @Override
            public void onFullscreenToggled(XWalkView view, boolean enterFullscreen) {
                if (enterFullscreen) {
                    client.onShowCustomView(view, new android.webkit.WebChromeClient.CustomViewCallback() {
                        @Override
                        public void onCustomViewHidden() {

                        }
                    });
                } else {
                    client.onHideCustomView();
                }
                super.onFullscreenToggled(view,enterFullscreen);

            }


        });


    }
*/

}

