# PHOLAR Lite.

## 기간

2017.09.05 ~ 2017.09.15

## 역할

Main, Gallery, Post Layout 및 기능 구현, Like, Notification 기능 구현

## 소개

Firebase 의 주요 기능 공부를 목적으로 NAVER APP 인 'PHOLAR' 를 카피하기로 했습니다.</br></br>
__PHOLAR Lite.__ 는 사진 공유 SNS 로 유저가 사진을 필수로 한 글을 작성하고, 그 글을 통해 사람들과 소통하는 APP 입니다.</br></br>
일주일이라는 짧은 기간 동안 개발을 통해 특수한 기능들을 빼고, 기존에 학습했던 기능들과
배워보고 싶고, 사용해보고 싶은 기능들을 위주로 구현하였습니다.</br>

## 개발 환경

- 개발 언어 : Java, JavaScript
- 개발 환경 : JDK 1.8, SDK(Min 16, Target 23)
- 데이터베이스 : Firebase Realtime DB
- 개발 도구 : Android Studio 3.0, Firebase Console

## 요약

![Skills&Library](https://github.com/Hooooong/Pholar/blob/master/img/skills%26Library.PNG)

## 화면

![screenshot1](https://github.com/Hooooong/Pholar/blob/master/img/screen1.PNG)
![screenshot2](https://github.com/Hooooong/Pholar/blob/master/img/screen2.PNG)
![screenshot3](https://github.com/Hooooong/Pholar/blob/master/img/screen3.PNG)
![screenshot4](https://github.com/Hooooong/Pholar/blob/master/img/screen4.PNG)

## 사용 Skills

1. Firebase Authentication

    - Google 계정 연동 로그인

2. Firebase Database & Storage

    - Firebase 에서 제공되는 Realtime DB 를 통해 Post 의 정보를 Create, Read

    - Firebase Storage 에 Post 에 관련된 사진 파일 업로드

3. Firebase Function & FCM ( Firebase Cloud Messaging )

    1. Post 의 `좋아요` 와 `댓글` 이 달리면 Post 작성자에게 알림(Notification)

        - Firebase Service 설정

        - `Retrofit` Service API 설정 [[소스코드]](https://github.com/Hooooong/Pholar/blob/master/app/src/main/java/com/hooooong/pholar/noti/IRetro.java)

        ```java
        @POST("sendLikeNotification")
        Call<ResponseBody> sendLikeNotification(@Body RequestBody post_data);
        ```

        - `좋아요`를 클릭했을 경우 통신 (댓글도 동일)[[소스코드]](https://github.com/Hooooong/Pholar/blob/master/app/src/main/java/com/hooooong/pholar/noti/SendNotification.java)

        ```java
        public static void sendLikeNotification(String post_id, String nickName, String token) {

            // Body 설정 + "\", \"imagePath\" : \"" +
            String json = "{\"to\": \"" + token + "\"" +
                    ", \"nickName\" : \"" + nickName +"\"" +
                    ", \"post_id\" : \"" +post_id +
                    "\"}";

            // 2. Firebase Function 에서 보내는 경우 application/json
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            // Retrofit 설정
            Retrofit retrofit = new Retrofit
                    .Builder()
                    // 2. Firebase Function 에서 보내는
                    .baseUrl("https://us-central1-pholar-f5bf3.cloudfunctions.net/")
                    .build();

            // Interface 결합
            IRetro service = retrofit.create(IRetro.class);

            // Service 로 연결 준비
            Call<ResponseBody> remote = service.sendLikeNotification(body);
            remote.enqueue(new Callback<ResponseBody>() {
                               @Override
                               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                   if (response.isSuccessful()) {
                                       Log.e("SendNotification", "sendLikeNotification 성공");
                                   }
                               }

                               @Override
                               public void onFailure(Call<ResponseBody> call, Throwable t) {
                                   Log.e("Retro", t.getMessage());
                               }
                           }
            );
        }
        ```

    2. Function 에 Script 를 작성하여 Notification 활성화

    ```JavaScript
    const fun = require("firebase-functions");
    const admin = require("firebase-admin");
    // const httpUrlConnection = require("request");

    admin.initializeApp(fun.config().firebase);

    exports.sendLikeNotification = fun.https.onRequest((req, res)=>{

        // 1. JSON Data Post 값을 수신
    	// "{\"to\": \"" + token + "\", " +"\"imagePath\" : \"" + post.getPhoto().get(0).storage_path +"\"" +", \"nickName\" : \""
    	// + nickName +", \"post_id\" : \"" + post.post_id +"\"}";
    	// req.body 내용에 추가적으로 정보를 보내 Title, body, click_action, sound 를 변경할 수 있다.
    	var dataObj = req.body;
    	// 전송할 메시지 객체를 완성
    	var msg = {
    		notification : {
    			title : "PHOLAR",
                body : dataObj.nickName +"님이 회원님의 포스팅에 좋아요를 눌렀습니다.",
                icon : "pholar_icon"

    			// intent-filter 의 action- name 의 값을 넣는다.
    			// Default 값을 넣어야 인식 한다.
                // ==== Android Manifest.xml ====
    			// <action android:name="NOTI_LAUNCHER" />
    			// <category android:name="android.intent.category.DEFAULT" />
    			// click_action : "POST_DETAIL",
    			// res/raw/파일명 을 작성하면 된다.
            },data :{
                nickName : dataObj.nickName,
    			post_id : dataObj.post_id,
    			flag : "detail"
            }
    	};

    	// Token 값을 배열로 해야 한다.
    	var tokens =[];
    	tokens.push(dataObj.to);

    	admin.messaging()
    		.sendToDevice(tokens, msg)
            // 요청이 정상적인지에 대한 콜백
    		.then(function(response){
    			res.status(200).send(response);
    		})
            // 요청에 대한 실패
            .catch(function(error){
    			res.status(500).send(error);
    		});
    });
    // 생략
    ```

4. RecyclerView

    1. ListView 와는 다르게 HeaderView 를 직접 작성

        - `onCreateViewHolder` 호출 시 viewType 을 통해 사용되는 view 를 설정

        ```java
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.each_write, parent, false);
                //inflate your layout and pass it to view holder
                return new VHItem(view);
            } else if (viewType == TYPE_HEADER) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.each_new_post, parent, false);
                return new VHHeader(view);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }
        ```

        - `getItemViewType` 설정

        ```java
        @Override
        public int getItemViewType(int position) {
            // 첫번째 아이템인지 체크하는 메소드 ( HeaderView 추가 )
            if (isPositionHeader(position))
                return TYPE_HEADER;

            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }
        ```

5. Permission

    1. Device 의 권한을 불러오기 위해 Permission 제어

        - `BaseActivity` 를 구현  [[소스코드]](https://github.com/Hooooong/Pholar/blob/master/app/src/main/java/com/hooooong/pholar/view/gallery/BaseActivity.java)

        ```java
        public abstract class BaseActivity extends AppCompatActivity {

            private static final int REQ_CODE = 999;
            private static String permissions[] = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            public abstract void init();

            public BaseActivity(String permissions[]){
                this.permissions = permissions;
            }

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                // 0. App 버전 체크
                // 마시멜로우는 version_code 가 이니셜로 되어 있어야 한다.
                // 마시멜로우 이후에만 Permission 정책이 바뀌었기 때문에
                // 현재 버전이 마시멜로우 이상이라면 Permission Check 를 하라고 알려주는 것이다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission();
                } else {
                    init();
                }
            }

            // @RequiresApi 애너테이션으로 API 의 메서드에 최소 API 레벨을 나타내는 예다.
            // 컴파일러에게 현재 버전이 마시멜로우 이상일 때 메소드를 실행하는 것이라고 알려주는 Annotation
            @RequiresApi(api = Build.VERSION_CODES.M)
            private void checkPermission() {
                // 1. 권한 유무 확인
                List<String> requires = new ArrayList<>();
                for (String permission : permissions) {
                    if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                        // READ_EXTERNAL_STORAGE 이 없다면
                        requires.add(permission);
                    }
                }

                if (requires.size() > 0) {
                    // 승인이 되지 않은 권한이 있을 경우에는 권한 요청
                    String perms[] = requires.toArray(new String[requires.size()]);
                    requestPermissions(perms, REQ_CODE);
                } else {
                    init();
                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                switch (requestCode){
                    case REQ_CODE:
                        boolean flag = true;
                        for(int grantResult : grantResults){
                            if(grantResult != PackageManager.PERMISSION_GRANTED){
                                flag = false;
                                break;
                            }
                        }
                        if(flag){
                            init();
                        }else{
                            Toast.makeText(this, "권한 승인을 하지 않으면 APP 을 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        break;
                }
            }
        }
        ```

        - 권한이 필요한 Activity에서 상속받아 사용 [[소스코드]](https://github.com/Hooooong/Pholar/blob/master/app/src/main/java/com/hooooong/pholar/view/gallery/GalleryActivity.java)

        ```java
        public class GalleryActivity extends BaseActivity {
            // 필요 권한 생성자에서 명시
            public GalleryActivity() {
                super(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
            }
            // 승인이 완료되면 init 메소드 실행
            @Override
            public void init() {
                setContentView(R.layout.activity_gallery);

                initLayout();
                initGalleryAdapter();
                setGallery();
            }
        }
        ```

6. ETC

    - Image Loading Library ( Glide )

    - Retrofit2 Library

## 소스 코드

  - [전체 소스코드](https://github.com/Hooooong/Pholar/tree/master/app/src/main/java/com/hooooong/pholar)
