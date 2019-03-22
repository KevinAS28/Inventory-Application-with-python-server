
import MySQLdb
#from printt import *
import os
#os.path.genericpath.os.abort

if __name__ != "__main__":
    ip = "127.0.0.1"
    user = "root"
    password = ""
    db_name = "mio"
    try:
        data_base = MySQLdb.connect(host=ip, user=user, passwd=password)
    except Exception as err:
        print("Cannot connect to mysql database")
     
     
db_name = "mio"
def Connect(db_name = db_name):
    MySQLdb.connect(host=ip, user=user, passwd=password)
    
"""
else:
    ip = input("ip: ")
    port = int(input("port: "))
    nama = input("nama database: ")
    user = input("username: ")
    password = input("password: ")
"""
#try:

#except Exception as err:
#    Error("Cannot connect to MYSQL database. the common problem is the MYSQL is not running or port is blocked")
def Run(order):    
    #try:
        data_base = MySQLdb.connect(host=ip, user=user, passwd=password)
        
        cur = data_base.cursor()
        cur.execute("use %s" %(db_name))
        cur.execute(order)
        
        hasil = []
        for i in cur.fetchall():
            hasil.append(i)
        data_base.commit()
        data_base.close()
        return hasil
    #except Exception as err:
    #    Error(str(err))
    #    return str(err)
def test():
    print("MYSQL Module is works")
if __name__ == "__main__":
    while True:
            order = input("perintah: ")
        #try:
            for i in Run(order):
                print(i)
        #except Exception as err:
        #    print(err)
def Create_Database():
    try:
        Run("drop database %s" %(db_name))
    except:
        pass
    #try:
        Run("create database %s" %(db_name))
        Run("use %s" %(db_name))
        Run("create table conf (aturan varchar(100) primary key, nilai varchar(100))")
        Info("Done.")
    #except Exception as err:
    #    Error("Cannot access mysql database\nplease make sure mysql is installed, Running, and accessible:\n %s" %(str(err)))
    #    raise SystemExit
def Check_Database():
    #try:
        Run("use %s" %(db_name))
        temp = Run("desc conf")
        if (temp[0][0]=='aturan' and temp[1][0] == "nilai"):
            return True
        raise ValueError
    #except Exception as err:
    #    print("Database doesnt exist\nCreating the new one...")
    #    Create_Database()
def Read_Conf(key="ALL"):
    #try:
        Run("use %s" %(db_name))
        if (key=="ALL"):
            return dict(Run('select * from conf'))
        else:
            return Run('select value from conf where rule="%s"' %(key))[0][0]
    #except Exception as err:
    #    Check_Database()
def Read_Conf_List(key="ALL"):
    return list(map(lambda x: x.replace(" ", ""), Read_Conf(key).split(",")))
    
def Read_DB(key, table):
    Run("use %s" %(db_name))
    return Run('select value from %s where rule="%s"' %(table, key))[0][0]

module_name = "MYSQL"


"""
    def Start_Server(self, server_name, protocol):
        printt.Info("Starting Server %s..." %(server_name))
        if True:   #try:
            

            
            
            #every server must have its own tools
            server = self.Load_Module(module=self.Server_List[self.Server_List.index(server_name)], dari="NeutronBrain_Servers", mode=1)
            
            
            #looking for running server with same name
            try:
                self.Running_Server[server.module_name]
                raise ValueError
            except ValueError:
                printt.Error("Another Server with same name already running")
                return
            except KeyError:
                pass
            
            #prepare the tools...
            server_conf_reader = self.Load_Module(module="logg", dari="NeutronBrain_Tools", mode=1)
            server_conf_reader.conf_file = server.conf_file
            
            Server_Conf_Read = server_conf_reader.Read_Conf
            Server_Conf_Read_List = server_conf_reader.Read_Conf_List
            Page_Lister = self.Page_Lister
            tools = [Server_Conf_Read, Server_Conf_Read_List, Page_Lister, printt, HTML, printt, file_manager]
            
            protocol = self.Load_Module(module=self.Protocol_List[self.Protocol_List.index(protocol)], dari="NeutronBrain_Protocols", mode=1)
            self.Running_Server[server.module_name] = server.Server(protocol, tools)
            Thread(target=self.Running_Server[server.module_name].Start, args=[]).start()
            printt.Info("Server %s Started" %(server_name))
        #except Exception as err:
        #    printt.Error("Error while starting server %s: %s" %(server_name, str(err)))
"""
