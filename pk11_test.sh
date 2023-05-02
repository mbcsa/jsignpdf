echo "pass123+" > /tmp/newpass.txt
echo "dsadasdasdasdadasdasdasdasdsadfwerwerjfdksdjfksdlfhjsdk" > /tmp/noise.txt
mkdir /tmp/nssdb
MODUTIL_CMD="modutil -force -dbdir /tmp/nssdb"
$MODUTIL_CMD -create
$MODUTIL_CMD -changepw "NSS Certificate DB" -newpwfile /tmp/newpass.txt
certutil -S -v 240 -k rsa -n "CN=localhost"  -t "u,u,u" -x -s "CN=localhost" -d /tmp/nssdb -f /tmp/newpass.txt -z /tmp/noise.txt
# https://bugzilla.redhat.com/show_bug.cgi?id=1760437
touch /tmp/nssdb/secmod.db

cat <<EOT >conf/pkcs11.cfg
name=testPkcs11
nssLibraryDirectory=/usr/lib/x86_64-linux-gnu
nssSecmodDirectory=/tmp/nssdb
nssModule=keystore
EOT

