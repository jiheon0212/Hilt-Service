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

- 상단 앱 바
	1. 내 정보화면으로 이동
	2. 뒤로가기 버튼

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
- addOnDestinationChangedListener가 정확하게 동작하지않고 bottom navigation view를 로그인 화면에서 보이게하는 문제
```
-> 조건문이 동작하는 시간과 UI에 적용되는 시간이 다른 관계로 발생하는 문제로 조건문을 외부에 두고 visibility를 검사하지 않고 listener 내부에서 처리해 이외 결과에는 전부 bottom navigation view가 보이지 않도록 설정
navControllerMain.addOnDestinationChangedListener { _, destination, _ ->
	if (checkControllerView()) {
		when (destination.id) {
			R.id.postFragment, R.id.chatRoomFragment, R.id.settingsFragment, R.id.boardFragment -> {
				binding.bottomNavigationView.visibility = View.VISIBLE
			}
			else -> {
				binding.bottomNavigationView.visibility = View.GONE
			}
		}
	} else binding.bottomNavigationView.visibility = View.GONE
}
```

### 배운 점

- Hilt Library에서 모든 view 관련 코드 작성에 android entry point를 적어줘야한다.
- child event listener는 변경이 일어난 항목에 대해서만 update가 진행되므로 post list 갱신을 위해서는 value event listener를 사용한다.
```
ValueEventListener는 특정 경로의 전체 데이터에 대한 변경 사항을 수신합니다. 데이터가 추가되거나, 변경되거나, 삭제될 때마다 전체 데이터 세트를 반환합니다.
ChildEventListener는 특정 경로의 하위 항목에 대한 변경 사항을 수신합니다. 하위 항목이 추가되거나, 변경되거나, 삭제되거나, 이동될 때마다 호출되는 메서드가 있습니다.
-> 두 listener의 차이점으로 인해 특정 post에 대한 수정, 삭제 처리는 ChildEventListener를 사용, post list 갱신은 ValueEventListener를 사용하는 것이 좋을 것 같다.
```
- 전 프로젝트인 끝말잇기 게임에서는 fragment 생명주기를 통해 사용자의 online status를 관리했는데 mainactivity에서 resume/stop을 통해 해당 container가 활성화 상태일 때만 status를 제어하니 사용자의 상태가 더욱 정확하게 표시된다.
```
private fun checkControllerView(): Boolean {
	val currentContainerViewType = binding.serviceContainer.visibility
	return if (currentContainerViewType == View.VISIBLE) true else false
}
```
- Hilt Library를 통한 의존성 관리를해서 한 곳에서 instance를 전부 초기화 진행하며 사용이 필요한 repository에만 부여하는 식으로 유지해 가독성이 매우 높아진다. 항목추가 & 수정을 해야하는 상황에 어떤 의존성을 부여할지 확실히 정할 수 있다.
- tab layout을 통한 navigation에 등록되지않은 화면에서의 이동을 구현하는 방법
- 하나 이상의 작업이 포함된 listener에는 await 메서드를 사용할 수 없으므로 callbackflow를 활용해 값을 비동기로 viewModel에 전달할 수 있다.
```
override fun updateBoard(): Flow<MutableList<Board>> = callbackFlow {
	val listenerRegistration = firestore.collection("board").document("Parenting").collection("Contents")
		.addSnapshotListener { snapshots, error ->
			...
			val boardList = mutableListOf<Board>()
			snapshots?.documents?.forEach { document ->
				val board = document.toObject(Board::class.java)
				if (board != null) {
					boardList.add(board)
				}
			}
			trySend(boardList).isSuccess
		}

	awaitClose { listenerRegistration.remove() }
}
```

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
