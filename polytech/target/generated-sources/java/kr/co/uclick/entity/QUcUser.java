package kr.co.uclick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUcUser is a Querydsl query type for UcUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUcUser extends EntityPathBase<UcUser> {

    private static final long serialVersionUID = 1871592516L;

    public static final QUcUser ucUser = new QUcUser("ucUser");

    public final StringPath address = createString("address");

    public final StringPath businessName = createString("businessName");

    public final StringPath departmentName = createString("departmentName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> lastUpdatedOn = createDateTime("lastUpdatedOn", java.util.Date.class);

    public final StringPath name = createString("name");

    public final ListPath<UcPhone, QUcPhone> phones = this.<UcPhone, QUcPhone>createList("phones", UcPhone.class, QUcPhone.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> registeredOn = createDateTime("registeredOn", java.util.Date.class);

    public QUcUser(String variable) {
        super(UcUser.class, forVariable(variable));
    }

    public QUcUser(Path<? extends UcUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUcUser(PathMetadata metadata) {
        super(UcUser.class, metadata);
    }

}

