class tes
{
 public static void main(String args[])
{
  byte a[] = {1, 2, 3};
 String b = new String(a);
 byte c[] = b.getBytes();
 System.out.println((int)c[0]);
}
}
