## 육아 대디를 위한 커뮤니티 앱

### 필요 기초 기능

- 로그인
- 게시물 올리기 & 댓글 & 추천 & 비추천 기능
- 1대1 메신저 기능
- 사용자 위치 Or 특정 위치를 기반으로 주변 키즈카페 정보를 가져오고 공유 & 저장 기능
- 소그룹을 만들고 정규 모임을 주최하는 기능
- 정보 공유 게시판
- 육아 물품 거래

### 화면 깊이 구조

- 로그인
  1. (익명/구글) 로그인
	   1. 사용자 정보 입력
	
- 홈(메인)
	1. 게시물 올리기
	2. 게시물 보이기
	3. 사용자 아이콘으로 채팅으로 연결
	4. 위치정보(키즈카페)화면 사용가능

- 메신저
   1. 채팅방 목록
      1. 1대1 채팅
		
- 소그룹
	1. 위치정보(키즈카페)화면 사용가능
	2. 모임 채팅방
	3. 약속(정규모임) 주최
	
- 게시판
	1. 게시판 내 검색
	2. 게시글 분류(육아/생활/정보/Etc)
	
- 거래
	1. 물품 올리기 (가격/이미지/거래장소)
	2. 1대1 채팅으로 연결
	3. 물품 보이기

### 상세 기능

- 로그인
  1.

- 게시물 올리기 & 댓글 & 추천 & 비추천 기능
	1.
	
- 1대1 메신저 기능
	1.
 
- 사용자 위치 Or 특정 위치를 기반으로 주변 키즈카페 정보를 가져오고 공유 & 저장 기능
	1.
	
- 소그룹을 만들고 정규 모임을 주최하는 기능
	1.
	
- 정보 공유 게시판
	1.
	
- 육아 물품 거래	
	1.
	
### 이미지

<img src="" width="25%" height="25%"></img>

### 버그

- Search view를 통한 view내에서 입력을 통한 탐색에서는 observer에 코드 작성 시 실시간 반영이 되지않으며 다른 livedata가 감지되면 오류로 인해 작동을 멈춘다.
```
-> observer로 등록하는 것이 아닌 livedata.value를 통해 현재 갱신된 최신 값의 value를 참조하여 filter를 반영한다.
private fun filterPost(query: String?) {
    val filteredPostList = viewModel.post.value?.filter { post ->
        post.postContent?.contentName!!.contains(query ?: "", ignoreCase = true)
    }?.toMutableList()
    homePostAdapter.fetchPost(filteredPostList!!)
}
```
- 
### 배운 점

- Hilt Library에서 모든 view 관련 코드 작성에 android entry point를 적어줘야한다.
- child event listener는 변경이 일어난 항목에 대해서만 update가 진행되므로 post list 갱신을 위해서는 value event listener를 사용한다.
```
ValueEventListener는 특정 경로의 전체 데이터에 대한 변경 사항을 수신합니다. 데이터가 추가되거나, 변경되거나, 삭제될 때마다 전체 데이터 세트를 반환합니다.
ChildEventListener는 특정 경로의 하위 항목에 대한 변경 사항을 수신합니다. 하위 항목이 추가되거나, 변경되거나, 삭제되거나, 이동될 때마다 호출되는 메서드가 있습니다.
-> 두 listener의 차이점으로 인해 특정 post에 대한 수정, 삭제 처리는 ChildEventListener를 사용, post list 갱신은 ValueEventListener를 사용하는 것이 좋을 것 같다.
```
-

### 사용 라이브러리
```
** Hilt **
implementation("com.google.dagger:hilt-android:2.51.1")
kapt("com.google.dagger:hilt-compiler:2.51.1")
** Lifecycle components **
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
** Firebase **
implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
implementation("com.google.firebase:firebase-analytics")
** Firebase Auth **
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.android.gms:play-services-auth:20.7.0")
** Firebase Realtime DB **
implementation("com.google.firebase:firebase-database-ktx")
** Firebase FireStore **
implementation("com.google.firebase:firebase-firestore")
** Jetpack Navigation **
val nav_version = "2.7.7"
implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
** retrofit2 **
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
** moshi **
implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
** okHttp **
implementation("com.squareup.okhttp3:okhttp:4.10.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
** gson **
implementation("com.squareup.retrofit2:converter-gson:2.9.0")	
```
