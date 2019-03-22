import MYSQL

MYSQL.db_name = "cms"
a = MYSQL.Run("show tables;")
for i in range(len(a)):
 a[i] = a[i][0]

hasil = {"":""}
result = {"":""}
keys = []
for i in a:
 b = MYSQL.Run("show columns from %s" %(i))
 for c in range(len(b)):
  b[c] = b[c][0]
 hasil[i] = b


for i in hasil:
 for b in hasil[i]:
  if (b in keys):
   continue
  keys.append(b)
  temp = []
  for c in hasil:
   if (b in hasil[c]):
    temp.append(c)
  result[b] = temp

for i in result:
 print("column %s ada di tabel: %s"%(i, str(result[i])), end="\n")
