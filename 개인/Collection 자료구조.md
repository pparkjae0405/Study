# Collection 자료구조

List Interface : 정렬된 인터페이스
```
순차 리스트(ArrayList) : 동적 배열 제공, 동기화 X
ArrayList<Integer> a = new ArrayList<Integer>();

연결 리스트(LinkedList) : 각각의 노드를 연결
LinkedList<Integer> a = new LinkedList<Integer>();

벡터(Vector) : 동적 배열 제공, 동기화 O
Vector<Integer> a = new Vector<Integer>();

스택(Stack) : 후입선출
Stack<Integer> a =new Stack<Integer>();
```

Queue Interface : 대기열 인터페이스
```
우선순위 큐(PriorityQueue) : 선입선출 기본, 우선순위 기반 처리
PriorityQueue<Integer> a = new PriorityQueue<Integer>();

- Deque Interface : 양방향 큐 인터페이스
순차 덱(ArrayDeque) : 크기 조정 배열, 양쪽 끝에서 요소 추가, 제거 가능
ArrayDeque<Integer> a = new ArrayDeque<Integer>();

연결 리스트(LinkedList) : 각각의 노드를 연결
Deque<String> a = new LinkedList<String>();
```

Set Interface : 중복값 저장 인터페이스
```
해시 셋(HashSet) : 동일 순서 보장X
HashSet<String> a = new HashSet<String>();

연결 해시 셋(LinkedHashSet) : 데이터 저장 순서 유지
LinkedHashSet<String> a = new LinkedHashSet<String>();

- SortedSet Interface : 순서 정렬 메서드 제공 인터페이스
트리셋(TreeSet) : Tree 사용 저장, 데이터 순서 오름차순 유지
TreeSet<String> a = new TreeSet<String>();
```

Map Interface : 키-값 매핑 데이터 인터페이스
```
해시맵(HashMap) : 키-값 형태 저장
HashMap<Integer, String> a = new HashMap<Integer, String>();
```