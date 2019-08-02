package com.xuge.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

public class ThriftClient {

    public static void main(String []args)throws Exception{
        TTransport transport=new TFastFramedTransport(new TSocket("localhost",10091),600);
        TProtocol protocol=new TCompactProtocol(transport);
        PersonService.Client client=new PersonService.Client(protocol);

        try {
            transport.open();
            Person person=client.getPersonByUsermae("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());
            System.out.println("------");

            Person person1=new Person();
            person1.setUsername("张锐扬");
            person1.setAge(30);
            person1.setMarried(true);
            client.savePerson(person1);
        }catch (Exception ex){
            throw new  RuntimeException(ex.getMessage(),ex);
        }finally {
            transport.close();
        }
    }
}
