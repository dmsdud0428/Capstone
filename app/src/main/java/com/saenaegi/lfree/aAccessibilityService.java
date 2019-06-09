package com.saenaegi.lfree;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.speech.tts.TextToSpeech;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.saenaegi.lfree.ListviewController.aListviewAdapter;
import com.saenaegi.lfree.ListviewController.aListviewItem;

public class aAccessibilityService extends AccessibilityService {
    private static final String TAG = "AccessibilityService";
    private TextToSpeech tts;              // TTS 변수 선언

    AccessibilityNodeInfo source;
    AccessibilityNodeInfo temp;
    //int tempGestureId;

    ArrayList<View> arr = new ArrayList<>();
    ArrayList<View> arr2 = new ArrayList<>();

    @Override
    public void onCreate() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.KOREAN);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplication(), "TTS : Korean Language Not Supported!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplication(), "TTS : TTS's Initialization is Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void listViewAutoFocusDown() {
        ArrayList<TextView> rowtemp = ((aRecentVideoActivity)aRecentVideoActivity.context).rowList;
        ArrayList<TextView> rowaftertemp = ((aRecentVideoActivity)aRecentVideoActivity.context).rowafterList;
        ListView listviewtemp = ((aRecentVideoActivity)aRecentVideoActivity.context).listView;
        aListviewAdapter aListviewAdaptertemp = ((aRecentVideoActivity)aRecentVideoActivity.context).adapter;
        TextView temp;
        ConstraintLayout constraintLayout;
        int focusposition = ((aRecentVideoActivity)aRecentVideoActivity.context).focusposition;

        if(source.getContentDescription().equals("영상 목록")) {
            temp = rowtemp.get(0);
            for(int j = 0 ; j < rowaftertemp.size() ; j++) {
                if(temp.getContentDescription().equals(rowaftertemp.get(j).getContentDescription())) {
                    listviewtemp.findViewsWithText(arr, temp.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                    listviewtemp.findViewsWithText(arr2, "constraint_" + temp.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                    arr2.get(0).setFocusable(true);
                    arr2.get(0).setFocusableInTouchMode(true);
                    arr2.get(0).performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                    //arr.get(0).setFocusable(true);
                    //arr.get(0).setFocusableInTouchMode(true);
                    //arr.get(0).performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                    //(listviewtemp.getAdapter().getView(0, null, listviewtemp)).setFocusable(true);
                    //(listviewtemp.getAdapter().getView(0, null, listviewtemp)).setFocusableInTouchMode(true);
                    //(listviewtemp.getAdapter().getView(0, null, listviewtemp)).setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
                    //(listviewtemp.getAdapter().getView(0, null, listviewtemp)).setContentDescription(arr.get(0).getContentDescription());
                    /*
                    constraintLayout = (ConstraintLayout) listviewtemp.getAdapter().getView(0, null, listviewtemp);
                    constraintLayout.setFocusable(true);
                    constraintLayout.setFocusableInTouchMode(true);
                    constraintLayout.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_AUTO);
                    constraintLayout.setContentDescription(arr.get(0).getContentDescription());
                    aListviewAdaptertemp.notifyDataSetChanged();
                    Log.e(TAG, "constraintLayout : " + constraintLayout);
                    Log.e(TAG, "constraintLayout ContentDescription : " + constraintLayout.getContentDescription());
                    ((ConstraintLayout)listviewtemp.getAdapter().getView(0, null, listviewtemp)).setContentDescription(constraintLayout.getContentDescription());
                    aListviewAdaptertemp.notifyDataSetChanged();
                    Log.e(TAG, "listLayout : " + (listviewtemp.getAdapter().getView(0, null, listviewtemp)));
                    Log.e(TAG, "list ContentDescription : " + (listviewtemp.getAdapter().getView(0, null, listviewtemp)).getContentDescription());
                    //constraintLayout.performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                    aListviewAdaptertemp.notifyDataSetChanged();
                    (listviewtemp.getAdapter().getView(0, null, listviewtemp)).performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                    */
                    arr2.remove(0);
                    arr.remove(0);
                    focusposition = 0;
                    return;
                }
            }
        }
    }

    public void listViewAutoFocusUp() {
        ArrayList<TextView> rowtemp = ((aRecentVideoActivity)aRecentVideoActivity.context).rowList;
        ArrayList<TextView> rowbeforetemp = ((aRecentVideoActivity)aRecentVideoActivity.context).rowbeforeList;
        ListView listviewtemp = ((aRecentVideoActivity)aRecentVideoActivity.context).listView;
        TextView temp;
        int focusposition = ((aRecentVideoActivity)aRecentVideoActivity.context).focusposition;

        if(source.getContentDescription().equals("영상 목록")) {
            temp = rowtemp.get(rowtemp.size()-1);
            for(int j = 0 ; j < rowbeforetemp.size() ; j++) {
                if(temp.getContentDescription().equals(rowbeforetemp.get(j).getContentDescription())) {
                    listviewtemp.findViewsWithText(arr, temp.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                    listviewtemp.findViewsWithText(arr2, "constraint_" + temp.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                    arr2.get(0).setFocusable(true);
                    arr2.get(0).setFocusableInTouchMode(true);
                    arr2.get(0).performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                    arr2.remove(0);
                    arr.remove(0);
                    focusposition = (rowtemp.size()-1);
                    return;
                }
            }
        }
    }

    public void listViewGestureLeft() {
        ActivityManager am3 = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti3 = am3.getRunningTasks(1);
        if((rti3.get(0).topActivity.getClassName()).contains("aRecentVideoActivity")) {
            ArrayList<TextView> rowtemp = ((aRecentVideoActivity)aRecentVideoActivity.context).rowList;
            ArrayList<TextView> rowaftertemp = ((aRecentVideoActivity)aRecentVideoActivity.context).rowafterList;
            ListView listviewtemp = ((aRecentVideoActivity)aRecentVideoActivity.context).listView;
            TextView temp;
            TextView tempafter;
            int focusposition = ((aRecentVideoActivity)aRecentVideoActivity.context).focusposition;

            if(rowtemp == null || rowaftertemp == null)
                return;

            for(int i = 0 ; i < rowtemp.size() ; i++) {
                temp = rowtemp.get(i);
                temp.setFocusable(true);
                temp.setFocusableInTouchMode(true);
                if ((source.getContentDescription()).equals("constraint_"+temp.getContentDescription())) {
                    if (temp.getId() == rowtemp.size() - 1) {
                        tempafter = rowtemp.get(0);
                        for(int j = 0 ; j < rowaftertemp.size() ; j++) {
                            if((tempafter.getContentDescription()).equals((rowaftertemp.get(j)).getContentDescription())) {
                                listviewtemp.findViewsWithText(arr, tempafter.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                                listviewtemp.findViewsWithText(arr2, "constraint_" + tempafter.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                                Log.e(TAG, "arr2 : " + arr2.get(0));
                                arr2.get(0).setFocusable(true);
                                arr2.get(0).setFocusableInTouchMode(true);
                                arr2.get(0).performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                                arr2.remove(0);
                                arr.remove(0);
                                focusposition = 0;
                                return;
                            }
                        }
                    }

                    else {
                        if(i + 1 == rowtemp.size())
                            break;
                        tempafter = rowtemp.get(i+1);
                        for(int j = 0 ; j < rowaftertemp.size() ; j++) {
                            if((tempafter.getContentDescription()).equals((rowaftertemp.get(j)).getContentDescription())) {
                                listviewtemp.findViewsWithText(arr, tempafter.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                                listviewtemp.findViewsWithText(arr2, "constraint_" + tempafter.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                                arr2.get(0).setFocusable(true);
                                arr2.get(0).setFocusableInTouchMode(true);
                                arr2.get(0).performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                                arr2.remove(0);
                                arr.remove(0);
                                focusposition = i + 1;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void listViewGestureRight() {
        ActivityManager am3 = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti3 = am3.getRunningTasks(1);
        if((rti3.get(0).topActivity.getClassName()).contains("aRecentVideoActivity")) {
            ArrayList<TextView> rowtemp = ((aRecentVideoActivity)aRecentVideoActivity.context).rowList;
            ArrayList<TextView> rowbeforetemp = ((aRecentVideoActivity)aRecentVideoActivity.context).rowbeforeList;
            ListView listviewtemp = ((aRecentVideoActivity)aRecentVideoActivity.context).listView;
            TextView temp;
            TextView tempbefore;
            int focusposition = ((aRecentVideoActivity)aRecentVideoActivity.context).focusposition;

            if(rowtemp == null || rowbeforetemp == null)
                return;

            for(int i = 0 ; i < rowtemp.size() ; i++) {
                temp = rowtemp.get(i);
                if ((source.getContentDescription()).equals("constraint_"+temp.getContentDescription())) {
                    if(temp.getId() == 0) {
                        tempbefore = rowtemp.get(rowtemp.size() - 1);
                        for(int j = 0 ; j < rowbeforetemp.size() ; j++) {
                            if((tempbefore.getContentDescription()).equals(rowbeforetemp.get(j).getContentDescription())) {
                                listviewtemp.findViewsWithText(arr, tempbefore.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                                listviewtemp.findViewsWithText(arr2, "constraint_" + tempbefore.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                                arr2.get(0).setFocusable(true);
                                arr2.get(0).setFocusableInTouchMode(true);
                                arr2.get(0).performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                                arr2.remove(0);
                                arr.remove(0);
                                focusposition = (rowtemp.size() - 1);
                                return;
                            }
                        }
                    }

                    else {
                        if(i -1 < 0)
                            break;
                        tempbefore = rowtemp.get(i - 1);
                        for(int j = 0 ; j < rowbeforetemp.size() ; j++) {
                            if((tempbefore.getContentDescription()).equals(rowbeforetemp.get(j).getContentDescription())) {
                                listviewtemp.findViewsWithText(arr, tempbefore.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                                listviewtemp.findViewsWithText(arr2, "constraint_" + tempbefore.getContentDescription(), ViewStub.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                                arr2.get(0).setFocusable(true);
                                arr2.get(0).setFocusableInTouchMode(true);
                                arr2.get(0).performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null);
                                arr2.remove(0);
                                arr.remove(0);
                                focusposition = i - 1;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onGesture(int gestureId) {
        if(source == null)
            return false;

        switch(gestureId) {
            //GESTURE_SWIPE_LEFT = 3
            //GESTURE_SWIPE_RIGHT = 4
            //GESTURE_SWIPE_UP = 1
            //GESTURE_SWIPE_DOWN = 2

            // 오른쪽에서 왼쪽으로 스와이프를 진행하는 경우, 해당 레이아웃 내에서 선택되어 있던 UI 컴포넌트의 다음 컴포넌트를 포커싱 및 선택
            case GESTURE_SWIPE_LEFT:
                Toast.makeText(getApplication(), "SWIPE_LEFT", Toast.LENGTH_LONG).show();
                if(source.getViewIdResourceName() != null) {
                    if(source.getContentDescription().equals("영상 목록") && source.getViewIdResourceName().contains("textView9")) {
                        listViewAutoFocusDown();
                        return true;
                    }

                    //if(source.getViewIdResourceName().contains("title")){
                    if(source.getViewIdResourceName().contains("constraint1")){
                        listViewGestureLeft();
                        return true;
                    }
                    //if((source.getViewIdResourceName()).contains("com.saenaegi.lfree:id/textView18"))
                    temp = source.getTraversalAfter();
                    if(temp == null)
                        return false;
                    temp.performAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS);
                    //source = temp;
                }

                else {
                    source = findFocus(AccessibilityNodeInfo.FOCUS_ACCESSIBILITY);
                }
                return true;

            // 왼쪽에서 오른쪽으로 스와이프를 진행하는 경우, 해당 레이아웃 내에서 선택되어 있던 UI 컴포넌트의 이전 컴포넌트를 포커싱 및 선택
            case GESTURE_SWIPE_RIGHT:
                Toast.makeText(getApplication(), "SWIPE_RIGHT", Toast.LENGTH_LONG).show();
                if(source.getViewIdResourceName() != null) {
                    if(source.getContentDescription().equals("영상 목록") && source.getViewIdResourceName().contains("textView9")) {
                        listViewAutoFocusUp();
                        return true;
                    }
                    //if(source.getViewIdResourceName().contains("title")) {
                    if(source.getViewIdResourceName().contains("constraint1")){
                        listViewGestureRight();
                        return true;
                    }
                    //if((source.getViewIdResourceName()).contains("com.saenaegi.lfree:id/textView18"))
                    temp = source.getTraversalBefore();
                    if(temp == null)
                        return false;
                    temp.performAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS);
                    //source = temp;
                }

                else {
                    source = findFocus(AccessibilityNodeInfo.FOCUS_ACCESSIBILITY);
                }
                return true;

            // 아래쪽에서 위쪽으로 스와이프를 진행하는 경우, 해당 레이아웃 UI에서 이전 레이아웃 UI로 이동
            case GESTURE_SWIPE_UP:
                Toast.makeText(getApplication(), "SWIPE_UP", Toast.LENGTH_LONG).show();
                ActivityManager am1 = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> rti1 = am1.getRunningTasks(1);
                if((rti1.get(0).topActivity.getClassName()).contains("aLfreeMainActivity")) {
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                    disableSelf();
                    System.runFinalizersOnExit(true);
                    System.exit(0);
                }
                else
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                //source = findFocus(AccessibilityNodeInfo.FOCUS_ACCESSIBILITY);
                return true;

            // 위쪽에서 아래쪽으로 스와이프를 진행하는 경우, 해당 레이아웃 UI에서 홈으로 이동
            case GESTURE_SWIPE_DOWN:
                Toast.makeText(getApplication(), "SWIPE_DOWN", Toast.LENGTH_LONG).show();
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                disableSelf();
                System.runFinalizersOnExit(true);
                System.exit(0);
                //source = findFocus(AccessibilityNodeInfo.FOCUS_ACCESSIBILITY);
                return true;
            default:
                Toast.makeText(getApplication(), "SWIPE_ETC", Toast.LENGTH_LONG).show();
                return false;
        }
    }

    // 이벤트가 발생할때마다 실행되는 부분
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "=========================================================================");
        Log.e(TAG, "Catch Event : " + event.toString());
        Log.e(TAG, "Catch Event Package Name : " + event.getPackageName());
        Log.e(TAG, "Catch Event TEXT : " + event.getText());
        Log.e(TAG, "Catch Event ContentDescription : " + event.getContentDescription());
        Log.e(TAG, "Catch Event getSource : " + event.getSource());
        // 발생한 이벤트로부터 Source를 get
        source = event.getSource();
        Log.e(TAG, "Catch View ID : " + source.getViewIdResourceName() + " " + source.getWindowId());
        Log.e(TAG, "Catch before or after item by focusing : " + source.getTraversalBefore() + " " +  source.getTraversalAfter());
        Log.e(TAG, "=========================================================================");
        // 실현 시간 상수로서 접근성 서비스에 대한 이벤트 타입 변수 선언 및 생성
        final int eventType =  event.getEventType();

        // 특정 이벤트에 대한 서술 변수
        String eventText = null;
        String introText = " \n실행하려면 두번 탭 하세요.";

        // 접근성 이벤트의 소스가 null이라면 예외가 발생한 것이므로 Side Effect가 발생하기 전에 바로 return
        if(source == null) {
            //Toast.makeText(getApplicationContext(), "Accessibility Service Source is NULL!", Toast.LENGTH_LONG).show();
            return;
        }

        if(source.getViewIdResourceName() == null || source.getContentDescription() == null) {
            //Toast.makeText(getApplicationContext(), "VIEW ID or Content is NULL!", Toast.LENGTH_LONG).show();
            return;
        }

        if(eventType == AccessibilityEvent.TYPE_VIEW_CLICKED || eventType == AccessibilityEvent.TYPE_VIEW_HOVER_ENTER) {
            // 이벤트를 발생시킨 해당 소스에 대한 Action 실행. 이 때의 Action은 접근성 서비스를 위한 FOCUS
            eventText = "클릭됨 : ";
            eventText = eventText + event.getContentDescription();
            source.performAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS);
            Toast.makeText(getApplication(), eventText, Toast.LENGTH_SHORT).show();
            // 다시 사용할 수 있도록 해당 인스턴스를 반환
            source.recycle();
        }

        else{
            switch (eventType) {
                // 특정 컴포넌트에 대해 접근성 관련 포커싱이 발생하면
                case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                    eventText = "접근성 포커싱됨 : ";
                    break;
                // AdapterView에 적재되어 있는 특정 갯수의 아이템이 선택될 시
                case AccessibilityEvent.TYPE_VIEW_SELECTED:
                    eventText = "선택됨 : ";
                    break;
                case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                    eventText = "포커싱됨 : ";
                    break;
                case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                    eventText = "스크롤 중 : ";
                    break;
            }
            // 이벤트의 대상이 된 컴포넌트의 ContentDescription 내용을 String에 저장 및 출력
            if(eventText != null) {
                eventText = eventText + event.getContentDescription();
                if(eventText.length() < 30 && eventType != AccessibilityEvent.TYPE_VIEW_SELECTED && eventType != AccessibilityEvent.TYPE_VIEW_FOCUSED && eventType != AccessibilityEvent.TYPE_VIEW_SCROLLED && eventType != AccessibilityEvent.TYPE_VIEW_CLICKED && !source.getViewIdResourceName().equalsIgnoreCase("com.saenaegi.lfree:id/textView9") && !source.getViewIdResourceName().equalsIgnoreCase("com.saenaegi.lfree:id/lfree"))
                    eventText = eventText + introText;
                    //else if(source.getViewIdResourceName().equalsIgnoreCase("com.saenaegi.lfree:id/title"))
                else if(source.getViewIdResourceName().equalsIgnoreCase("com.saenaegi.lfree:id/constraint1")) {
                    eventText = eventText.replace("constraint_", "");
                    eventText = eventText + introText;
                }
                Toast.makeText(getApplication(), eventText, Toast.LENGTH_SHORT).show();

                // TTS 기능으로 말하기
                // TextToSpeech은 버전에 따라 다르게 구성
                if (eventType != AccessibilityEvent.TYPE_VIEW_SCROLLED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak(eventText, TextToSpeech.QUEUE_FLUSH, null, "TextToSpeech_ID");
                    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak(eventText, TextToSpeech.QUEUE_FLUSH, null);
                    }
                } else if (eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak("스크롤 중", TextToSpeech.QUEUE_FLUSH, null, "TextToSpeech_ID");
                    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak("스크롤 중", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }

            else
                return;
        }
    }

    // 접근성 권한을 가지고, 연결이 되면 호출되는 함수
    @Override
    public void onServiceConnected() {
        Toast.makeText(getApplication(), "LFREE Accessibility Service : Connected!", Toast.LENGTH_LONG).show();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();

        // 새내기 애플리케이션에서만 접근성 서비스가 동작 시작하도록 설정. 물론 동작 시작된 서비스는 다른 앱에서도 동작되게 됨.
        info.packageNames = new String[]{"com.saenaegi.lfree"};

        // 뷰 클릭 시, 뷰 포커싱 시, 제스처 인식 시, 검색창 텍스트 변경 시 접근성 서비스 이용을 위한 이벤트 타입 수집
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_FOCUSED
                | AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START | AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END
                | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED | AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED
                | AccessibilityEvent.TYPE_VIEW_SELECTED | AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED | AccessibilityEvent.TYPE_VIEW_HOVER_ENTER;

        //info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK; // 전체 이벤트 가져오기

        // 이벤트 발생 시 음성 및 진동 피드백 제공
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN | AccessibilityServiceInfo.FEEDBACK_HAPTIC;
        //info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;

        // 제스처 기능 수행을 위한 터치 모드 수행 플래그 설정
        info.flags = AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE | AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS |
                AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS | AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        //info.flags = AccessibilityServiceInfo.FLAG_REQUEST_FINGERPRINT_GESTURES;
        info.notificationTimeout = 100; // millisecond
        // 서비스 설정
        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {
        // 특정 컴포넌트에 대한 tts 기능 사용 후에는 반드시 shutdown을 시켜 리소스 낭비를 피하도록 한다.
        // 이 때 TTS 기능이 shutdown이 되더라도 다시 enable하면 되므로 상관없다.
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}