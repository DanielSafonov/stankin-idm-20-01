# Email
stankin92614f2b@gmail.com
92614f2b-4e25-4c8b-a3f4-53c4ec272023

# Sign
cd ~/resources
### generate key pair
keytool -genkeypair -alias senderKeyPair -keyalg RSA -keysize 2048 -dname "CN=Stankin" -validity 365 -storetype PKCS12 -keystore sender_keystore.p12 -storepass changeit
### publish public key from keypair
keytool -exportcert -alias senderKeyPair -storetype PKCS12 -keystore sender_keystore.p12 -file sender_certificate.cer -rfc -storepass changeit
### loading public key for verification
keytool -importcert -alias receiverKeyPair -storetype PKCS12 -keystore receiver_keystore.p12 -file sender_certificate.cer -rfc -storepass changeit