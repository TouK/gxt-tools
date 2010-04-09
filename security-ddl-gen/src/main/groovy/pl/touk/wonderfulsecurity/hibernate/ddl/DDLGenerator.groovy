/*
* Copyright (c) 2008 TouK.pl
* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package pl.touk.wonderfulsecurity.hibernate.ddl;


import org.hibernate.cfg.Configuration
import org.hibernate.dialect.resolver.DialectFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.orm.hibernate3.LocalSessionFactoryBean

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class DDLGenerator {

  public static void main(String[] args) {

    if (!args || args.length != 1) {
      throw new IllegalStateException("DDLGenerator main method takes exactly one parameter which is output directory")
    }

    def outputDir = args[0]

    def dialects = ["org.hibernate.dialect.PostgreSQLDialect",
            "org.hibernate.dialect.MySQLDialect",
            "org.hibernate.dialect.MySQLInnoDBDialect",
            "org.hibernate.dialect.MySQLMyISAMDialect",
            "org.hibernate.dialect.OracleDialect",
            "org.hibernate.dialect.Oracle9Dialect",
            "org.hibernate.dialect.SQLServerDialect",
            "org.hibernate.dialect.HSQLDialect"
    ]

    ApplicationContext springCtx = new ClassPathXmlApplicationContext("/ws-ddl-generator-context.xml");
    // make sure factory is initialized properly
    springCtx.getBean("ddlHibernateSessionFactory")
    LocalSessionFactoryBean session = (LocalSessionFactoryBean) springCtx.getBean("&ddlHibernateSessionFactory");
    Configuration configuration = session.getConfiguration();

    def ant = new AntBuilder();

    dialects.each {
      def dialect = DialectFactory.constructDialect(it);
      String[] drop = configuration.generateDropSchemaScript(dialect);
      String[] create = configuration.generateSchemaCreationScript(dialect);

      def dropHandle = new File("${outputDir}/${dialect.class.name}.DROP-SCRIPT.sql")
      def createHandle = new File("${outputDir}/${dialect.class.name}.CREATE-SCRIPT.sql")
      ant.touch(file: dropHandle, mkdirs: true, verbose: true)
      ant.touch(file: createHandle, mkdirs: true, verbose: true)


      dropHandle.write(drop.join(";\n") + ";\n")
      createHandle.write(create.join(";\n") + ";\n")

    }


  }
}
