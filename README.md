Android Programing
----------------------------------------------------
### 2017.11.15 33일차

#### 예제
____________________________________________________

#### 공부정리
____________________________________________________

##### __RxJava Subject__

- RxJava Subject란?

  > RxJava 에서 사용되는 Subject 는 Observable 과 Observer 를 합친 개념이다. Observable(발행) 과 동시에 Observer(구독)을 할 수 있는 역할을 가지고 있다. 독립적으로 사용될 목적으로 만들어진 것이 아닌, 중간자(Proxy)의 역할로 만들어진 것으로 아이템(데이터)을 발행하고 있는 Observable 들을 바라봐 데이터를 합치거나, 구독 시점을 변경할 수 있다.

- RxJava Subject 종류

  1. Publish Subject

      ![Publish Subject](https://github.com/Hooooong/DAY40_RxJava3/blob/master/image/PublishSubject.png)

      - Publish Subject 는 아이템(데이터)를 발행(onNext)을 하고 구독(subscribe)을 하기 시작하면, 그 시점부터 데이터를 읽을 수 있다.

      - 이전에 발행된 아이템(데이터)은 읽을 수 없다.

  2. Behavior Subject

      ![Behavior Subject](https://github.com/Hooooong/DAY40_RxJava3/blob/master/image/BehaviorSubject.png)

      - 구독(subscribe)을 하면 최근에 발행한 아이템(데이터)부터 구독한다.

  3. Replay Subject

      ![Replay Subject](https://github.com/Hooooong/DAY40_RxJava3/blob/master/image/ReplaySubject.png)

      - 구독(subscribe)을 한 시점에 상관없이 모든 데이터를 읽을 수 있다.

  4. Async Subject

      ![Async Subject](https://github.com/Hooooong/DAY40_RxJava3/blob/master/image/AsyncSubject.png)

      - 구독(subscribe) 하면 마지막 아이템(데이터)을 구독하고 Observable의 동작이 완료된 후에야 동작한다. (만약, Observable이 아무 값도 발행하지 않으면 AsyncSubject 역시 아무 값도 배출하지 않는다.)

- 참조 : [RxJava Subject](http://reactivex.io/documentation/ko/subject.html)
