package com.efub.blogsession.global.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass//객체 입장에서 공통 매핑 정보가 필요할 떄 사용, JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우, 필드들도 컬럼으로 인식하도록 설정
// @MappedSuperclass가 붙은 클래스는 엔티티도 아니고, 테이블 매핑도 안된다.
@EntityListeners(AuditingEntityListener.class)//Auditing 기능(Spring Date JPA에서 시간에 대해 자동으로 값을 넣는 기능)을 포함시킨다.
public abstract class BaseTimeEntity {
	// 모든 Entity의 상위 클래스가 되어, createDate와 modifiedDate를 자동 관리하는 역할

	@CreatedDate// Entity가 생성되어 저장될 때(최초 생성) 시간을 저장한다.
	@Column(updatable = false) // 최초 생성 시간을 기록하는 것이므로 수정할 일이 없다. 따라서 해당 컬럼은 수정되지 않게 막는다.
	// + 업데이트시 해당 값이 입력이 없는 경우 NULL이 될 수 있는데 update=false를 통해 update 시 NULL값이 들어오는 것을 막을 수 있다.
	private LocalDateTime createdDate;

	@LastModifiedDate//엔티티의 변경이 있으면 해당 시간을 저장한다.
	@Column(insertable=false) // 해당 컬럼은 DB 삽입에서 제외한다. 처음에는 DB에 넣을 때는 아직 수정하기 전이므로 비워져 있어야 한다.
	private LocalDateTime modifiedDate;
}
