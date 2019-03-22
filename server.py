import socket
import threading
import os
import MYSQL
import datetime
import xlwt

if __name__=="__main__":
	server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
	server.bind(("0.0.0.0", 7777))
	server.listen()

def tambah_barang(args):
	nama = args[0]
	jmlh = args[1]
	jenis = args[2]
	last_id = MYSQL.Run("select * from barang")[-1][0]
	new_id = "B_"+str(int(last_id.split("_")[1])+1)
	MYSQL.Run("insert into barang(id_barang, nama, stok, id_jenis) values('%s', '%s', '%s', '%s')"%(new_id, nama, jmlh, jenis))
	return ""
"""	
	out = MYSQL.Run(query)
	tosend = ""
	print(out)
	for i in out:
		for a in i:
			tosend+=str(a)+","
		tosend+=";"	

"""

def run(args):
	q = args[0]
	a = MYSQL.Run(q)
	toreturn = ""
	for i in a:
		for b in i:
			toreturn+=str(b)+","
		toreturn+=";"
	return toreturn

def inventariskan(args):
	for i in range(len(args)):
		if(args[i]==""):
			del args[i]
	for i in range(len(args)):
		args[i] = args[i].split("|")
	id_inv = ""
	id_peminjam = args[0][0];
	id_petugas = args[0][1];
		
	if ((MYSQL.Run("select count(*) from inventaris")[0][0])==0):
		id_inv = "IN_0"
	else:
		id_inv = "IN_"+str((int((MYSQL.Run("SELECT * FROM inventaris ORDER BY id_inventaris DESC LIMIT 1")[0][0]).split("_")[1])+1))
	query = "insert into inventaris(id_inventaris, tanggal_pinjam, id_peminjam, id_petugas) values('%s', '%s', '%s', '%s')"%(id_inv, str(datetime.datetime.now()), id_peminjam, id_petugas)
	MYSQL.Run(query)
	for i in args[1:]:
		#stok barangnya
		stock = int(MYSQL.Run("select stok from barang where id_barang='%s'"%(i[0]))[0][0])
		if (int(i[1])>stock):
			i[1] = str(stock)
		sisa = stock - int(i[1])
		if ((MYSQL.Run("select count(*) from pinjaman_barang")[0][0])==0):
			id_pinjam_barang = "MB_0"
		else:
			id_pinjam_barang = "MB_"+str((int((MYSQL.Run("SELECT * FROM pinjaman_barang ORDER BY id_pinjaman_barang DESC LIMIT 1")[0][0]).split("_")[1])+1))

		MYSQL.Run("update barang set stok='%d' where id_barang='%s'"%(sisa, i[0]))
		q = "insert into pinjaman_barang(id_pinjaman_barang, id_inventaris, id_barang, jumlah) values('%s', '%s', '%s', '%s')"%(id_pinjam_barang, id_inv, i[0], i[1])
		print("inventaris: ", q)
		MYSQL.Run(q)
	return "true"
def pengembalian(args):
	pinjam_key = args[0]
	id_pengembali = args[1]
	dikembalikan = args[2]
	id_b = args[3]
	q = MYSQL.Run("select * from pinjaman_barang where id_pinjaman_barang='%s'"%(pinjam_key))[0]
	if (int(dikembalikan)>int(q[3])):
		dikembalikan = q[3]
	sisa = str(int(q[3])-int(dikembalikan))
	q0 = "update pinjaman_barang set dikembalikan='%s', jumlah='%s', terakhir_pengembalian='%s' where id_pinjaman_barang='%s'"%(dikembalikan,sisa, str(datetime.datetime.now(
	        )),pinjam_key)
	
	MYSQL.Run(q0)
	awal = MYSQL.Run("select stok from barang where id_barang='%s'"%(id_b))[0][0]
	MYSQL.Run("update barang set stok='%s' where id_barang='%s'"%(str(int(awal)+int(dikembalikan)),id_b))
	return "true"
def laporan(args):
	wb = xlwt.Workbook()
	
	q0 = MYSQL.Run("select * from inventaris")
	bold = xlwt.easyxf('font: bold 1')
	ws = wb.add_sheet("Laporan")
	
	column = [ "ID INVENTARIS", "ID PINJAMAN BARANG","PEMINJAM", "ID PEMINJAM", "PETUGAS", "ID PETUGAS", "NAMA BARANG", "ID BARANG", "JUMLAH", "DIKEMBALIKAN", "TANGGAL PINJAM", "TERAKHIR DIKEMBALIKAN"]
	for a in range(len(column)):
		ws.write(0, a, column[a], bold)
	
	cur_row = 1
	for i in q0:
		id_inventaris =  i[0]
		tgl_pinjam =  str(i[1])
		id_peminjam =  i[2]
		id_petugas =  i[3]
		nama_peminjam = MYSQL.Run("select nama from seluruh_user where id='%s'"%(id_peminjam))[0][0]
		nama_petugas = MYSQL.Run("select nama from seluruh_user where id='%s'"%(id_petugas))[0][0]
		
		q1 = MYSQL.Run("select * from pinjaman_barang where id_inventaris='%s'"%(i[0]))
		
		for b in q1:
			id_pinjaman_barang = b[0]
			id_barang = b[2]
			jumlah = b[3]
			dikembalikan = b[4]
			terakhir_kembali = b[5]
			nama_barang = MYSQL.Run("select nama from barang where id_barang='%s'"%(id_barang))[0][0]
			to_add = [id_inventaris, id_pinjaman_barang, nama_peminjam, id_peminjam, nama_petugas, id_petugas, nama_barang, id_barang, jumlah, dikembalikan, tgl_pinjam, terakhir_kembali]
			for z in range(len(to_add)):
				#print("ws.write(%s, %s, %s)"%(cur_row, z, to_add[z]))
				ws.write(cur_row, z, to_add[z])
			cur_row+=1
			
	wb.save('laporan.xls') 
	with open("laporan.xls", "rb") as yay:
		baca = yay.read()
	return baca
def login(args):
	username, password = args[0].split("|")
	a = MYSQL.Run("select id_user from login where email='%s' and password='%s'"%(username, password))

	if (len(a)==0):
		return "false"
	else:
		return a[0][0]
func = {"tambah_barang": tambah_barang, "run": run, "inventariskan": inventariskan, "pengembalian": pengembalian, "login": login, "laporan": laporan}
def client_handler(client, addr):
	recv = (client.recv(1024))
	print("pure recv: ", recv)
	recv = recv.decode("utf-8")[2:].split(";")
	print("splitted recv: ", recv)
	tosend = func[recv[0]](recv[1:])
	print("tosend: %s"%(tosend))
	if (type(tosend)==str):
		tosend = tosend.encode('utf-8')
	client.send(tosend)
	client.close()

def main():
	print("Server Started")
	finish = False
	while (not finish):
	 client, addr = server.accept()
	 threading.Thread(target=client_handler, args=[client, addr]).start()
if (__name__=="__main__"):
	main()