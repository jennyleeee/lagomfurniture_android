package com.example.lagomfurniture.utils.api.kakao;

import android.content.Context;

import androidx.annotation.Nullable;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

public class KakaoSDKAdapter extends KakaoAdapter {
    /**
     * Session Config에 대해서는 Default 값들이 존재합니다.
     * 필요한 상황에서만 override 해서 사용하면 됩니다.
     * @return Seesion의 설정값.
     * 로그인시 사용 될, Session의 옵션 설정을 위한 인터페이스
     */
    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            /**
             * Auth Type. 로그인 시 인증 타입을 지정합니다.
             * KAKAO_TALK : 카카오톡 로그인 타입
             * KAKAO_STORY : 카카오 스토리 로그인 타입
             * KAKAO_ACCOUNT : 웹뷰 다이어로그를 통한 계정 연결 타입
             * KAKAO_LOGIN_ALL : 모든 로그인 방식을 제공
             * @return Auth Type
             */
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
            }

            /**
             * 로그인 웹뷰 에서 pause 와 resume 시에 타이머를 설정하여,
             * CPU 의 리소스 소모를 절약할 지 여부를 지정합니다.
             * true 로 지정할 경우, 로그인 웹뷰의 onPause()와 onResume()에
             * 타이머를 설정해야 합니다.
             * @return false;
             */
            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            /**
             * 로그인 시 토큰을 저장할 때 암호화 여부를 지정합니다.
             * @return
             */
            @Override
            public boolean isSecureMode() {
                return false;
            }

            /**
             * 일반 사용자가 아닌 KAKAO와 제휴된 앱에서 사용하는 값입니다.
             * 값을 지정하지 않을 경우,
             * ApprovalType.INDIVIDUAL 값으로 사용 됩니다.
             * @return INDIVIDUAL
             */
            @Nullable
            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            /**
             * 로그인 웹뷰에서 Email 입력 폼의 데이터를 저장할 지 여부를 지정합니다.
             * @return true
             */
            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }

    /**
     * KakaoSDK에서 Application에 필요한 정보를 얻기 위한 용도로 사용된다.
     *
     * @return Application context 정보를 가져올 수 있는 IApplicationConfig 객체
     */
    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
            @Override
            public Context getApplicationContext() {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
    }


}