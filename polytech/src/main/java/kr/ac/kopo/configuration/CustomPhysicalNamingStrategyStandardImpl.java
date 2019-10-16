package kr.ac.kopo.configuration;

import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategyStandardImpl extends PhysicalNamingStrategyStandardImpl {
//	MySQL의 경우에 테이블 이름을 Underscore(_)로 구분하는게 아니라 대문자 형식(ex: MyTable)으로 작성하는 경우가 있다.
//	이 경우에는 Entity의 테이블 이름을 MyTable로 주면 Spring JPA에서 자동으로 my_table로 변경해버려서 테이블을 찾지 못한다고 이야기하는 경우가 있다.
//	이 때는 PhysicalNamingStrategyStandardImpl를 상속하면된다.
	private static final long serialVersionUID = -6972754781554141247L;
	//serialVersionUID 는 직렬화에 사용되는 고유 아이디인데, 선언하지 않으면 JVM에서 디폴트로 자동 생성된다.
	//따라서 선언하지 않아도 동작하는데 문제는 없지만, 불안하기 때문에 JAVA에서는 명시적으로 serialVersionUID를 선언할 것을 적극 권장하고 있다.

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	protected static String addUnderscores(String name) {
		final StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase(Locale.ROOT);
	}
}
