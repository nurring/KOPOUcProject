package kr.ac.kopo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUcPhone is a Querydsl query type for UcPhone
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUcPhone extends EntityPathBase<UcPhone> {

    private static final long serialVersionUID = -1991182373L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUcPhone ucPhone = new QUcPhone("ucPhone");

    public final StringPath carrier = createString("carrier");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phone = createString("phone");

    public final QUcUser ucUser;

    public QUcPhone(String variable) {
        this(UcPhone.class, forVariable(variable), INITS);
    }

    public QUcPhone(Path<? extends UcPhone> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUcPhone(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUcPhone(PathMetadata metadata, PathInits inits) {
        this(UcPhone.class, metadata, inits);
    }

    public QUcPhone(Class<? extends UcPhone> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ucUser = inits.isInitialized("ucUser") ? new QUcUser(forProperty("ucUser")) : null;
    }

}

