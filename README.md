# 📗 EFUB 3기 세션 수행 📗

-해당 세션 주차의 목요일까지

# efub3-backend-session-seminar  

# 클래스와 객체  
클래스  
• 객체를 만들기 위한 설계도  
• field(상태)와 method(동작)로 나뉨  
• ex. 고양이를 field와 method로 나누기  
• field: 이름, 나이, 체중  
• method: 할퀴기, 야옹  

객체 (==인스턴스)
• 물리적으로 존재하거나 추상적으로 생각할 수 있는 것 중에서 자신과 다른 것을 식별 가능한 것  
• 클래스를 통해 만들어진 것  

#다형성  
하나의 객체나 메소드가 여러가지 다른 형태를 가질 수 있는 것  
• Java에서 오버라이딩, 오버로딩, 형변환 등으로 나타낼 수 있음  
• 오버라이딩: 부모 클래스에 있는 메소드를 자식 클래스에서 재정의하여 사용하는 것  
• 오버로딩: 같은 이름의 메소드를 매개변수의 타입이나 개수에 따라 중복 정의함으로써 매개변수에
따라 특정 메소드를 호출  

#생성자
객체가 생성될 때 자동으로 호출되는 메소드  
• 객체의 초기화를 위해 사용됨  
• 생성자의 이름은 해당 클래스의 이름과 같아야 함  
• return 타입이 없음  
• 매개변수 조건에 따라 오버로딩 가능  
• 클래스에 최소 1개는 있어야 함  

##Spring
#container
인스턴스의 생명주기를 관리  
• 생성된 인스턴스들에게 추가적인 기능 제공  
Spring Container  
Spring에서 자바 객체(Bean)들을 관리하는 공간  
• 객체의 생성부터 소멸까지 개발자 대신 관리  
• 각 객체 간의 의존 관계를 연결  

Singleton 패턴  
클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴  
• 객체의 공유를 통해 메모리 낭비를 줄이기 위해 사용  
특징  
• static 영역에 객체 인스턴스를 생성  
• private으로 new 키워드를 막음  
• getInstance() 키워드를 통해서만 조회 가능  
* 문제점 : 복잡한 코드  
• 구체 클래스.getInstance()로 객체 조회  
䡸 클라이언트가 구체 클래스에 의존함으로써 DIP 위반  
• private 생성자로 자식 클래스를 만들기 어려움  

Bean Factory  
Spring Container의 최상위 인터페이스  
• Spring Bean을 생성하고, 객체 사이의 런타임 의존관계를 맺어주는 역할을 함  
• 직접적으로 사용하는 경우는 거의 없음  

Bean   
Spring Container가 관리하는 자바 객체  
• Spring Container에 의해 생성, 관리됨  
• Bean 이름은 항상 다르게 설정해야 함 䡸 Singleton 패턴 때문  
Bean 관련 메서드  
1) getBeanDefinitionNames() : 스프링에 등록된 모든 Bean 이름을 조회  
2) getBean() : 특정 Bean을 조회하는데 사용  
• ac.getBean(classType)  
• ac.getBean(beanA)  
• ac.getBean(beanA, classType)  
䡸 Bean이 존재하지 않는 경우, NoSuchDefinitionException 발생  
3) getBeansOfType() : 특정 타입에 해당하는 모든 Bean 조회  
 
IoC 제어의 역전  
프로그램의 제어 흐름을 개발자가 아닌 외부에서 관리  
• 객체의 생성과 소멸, 객체 간의 의존관계 설정을 모두 Spring에서 제어  
• 객체의 의존성을 역전시켜 모듈 간의 의존도를 줄이고 유연한 코드를 작성할 수 있게 함  
• IoC가 이루어지는 공간 == IoC 컨테이너  

DI 의존성 주입  
클래스 사이의 의존 관계를 Bean 설정 정보를 바탕으로 자동 연결해주는 것  
의존성 주입 방법  
생성자 주입  
• Spring에서 가장 권장함  
• 대부분의 의존관계 주입은 한 번 일어날 경우 애플리케이션 종료시까지 의존관계를 변경할 일 X  
 생성자 주입은 객체를 호출할 때 딱 한 번만 호출되므로 불변하게 설계 가능  
• 순수 자바 코드를 통해 단위 테스트를 하는 경우 의존관계가 누락되었을 때 컴파일 에러를 통해
바로 캐치할 수 있음  
• 순환 의존성 검출 가능 (BeanCurrentlyCreationException 발생)  
 프레임워크에 의존하지 않고 순수 자바 언어의 특징을 가장 잘 살리는 방법 
 - @RequiredArgsComstructor를 함께 사용 시 필요한 기능을 더 깔끔하게 사용 가능

Lombok   
반복되는 메서드를 어노테이션을 사용하여 자동으로 작성해주는 라이브러리  
• Java에 사용하는 메서드가 많아질수록 코드가 복잡해지고 생성하는데 번거로워짐  
䡸 Lombok을 사용하여 코드 다이어트  
• 어노테이션 기반의 코드 자동 생성을 통한 생산성 향상  
• 반복 코드 다이어트를 통해 가독성 및 유지보수성 향상  

의존성 주입 방법  
Spring, Lombok을 결합한 생성자 주입  
• @RequiredArgsConstructor를 사용하는 방법  
• Lombok 라이브러리를 통해 생성자를 생성 시 Spring 프레임워크가 해당 생성자에 @Autowired가
생략되었다는 것을 인식 시 적합한 Spring Bean 주입  
• Spring 4.3부터 사용 가능

Lombok Annotation  
생성자 관련  
• @NoArgsConstructor: 매개 변수가 없는 기본 생성자를 생성  
• @RequiredArgsConstructor: final 필드만 포함된 생성자를 생성  
• @AllArgsConstructor: 모든 필드를 포함한 생성자를 생성  

Lombok Annotation   
메서드 관련  
• @Getter/@Setter: Getter/Setter를 자동으로 생성  
• @toString: toString 메서드를 자동으로 생성  
• @EqualsAndHashCode: equals, hashCode를 자동으로 생성  

Lombok Annotation  
빌더 패턴  
• @Builder: 메서드 체이닝을 이용하는 static 메서드 builder를 생성  
통합 기능  
• @Data: @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor를
한꺼번에 제공  
