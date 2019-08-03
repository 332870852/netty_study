from py.thrift.generated import PersonService
from py.thrift.generated import ttypes

from thrift import Thrift
from thrift.transport import TSocket, TTransport
from thrift.protocol import TCompactProtocol

try:
    tSocket=TSocket.TSocket('locahost',10091)
    tSocket.setTimeout(600)
    transport=TTransport.TFramedTransport(tSocket)
    protocol=TCompactProtocol.TCompactProtocol(transport)
    client=PersonService.Client(protocol)

    transport.open()
    person=client.getPersonByUsermae('张三')
    print(person.username)
    print(person.age)
    print(person.married)

    print('--------')
    newPerson=ttypes.Person()
    newPerson.username='lisi'
    newPerson.age=30
    newPerson.married=True
    client.savePerson(newPerson)
    transport.close()

except Thrift.TException as tx:
    print('%s'% tx.message)