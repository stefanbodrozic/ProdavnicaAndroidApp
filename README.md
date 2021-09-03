Implementirati Android aplikaciju za rad sa porudžbinama koju koriste prijavljeni korisnici -
administratori, prodavci i kupci. Prilikom startovanja aplikacije implementirati početni ekran
(SplashActivity) kroz koju se korisnik uvodi u samu aplikaciju (prikazuje se logo aplikacije i/ili
njen naziv). Kroz ovu aktivnost se proverava da li je korisnik prethodno prijavljen, te ukoliko
jeste preusmerava se na glavni prikaz (opisan u nastavku specifikacije), dok ukoliko korisnik nije
bio prethodno prijavljen, nakon početnog prozora korisnika biva preusmeren na stranicu prijavu.

Prijava/Odjava
U okviru ove aktivnosti korisniku je omogućena prijava na sistem. Pored prikaza forme za unos
kredencijala, na istom prikazu uključiti i link za prelaz na formu za registraciju. U okviru
aplikacije podržati opciju za odjavu, koja će biti dostupna u Toolbar meniju.

Registracija
Prilikom registracije na sistem osim korisničkih podataka, korisnik bira i ulogu (prodavac ili
kupac). Nakon uspešne registracije preusmeriti korisnika na aktivnost za prijavu na sistem.

Rad sa komentarima
Svaki prodavac ima uvid u komentare koje su kupci ostavili nakon isporuke porudžbina. Svaki
prodavac može da izabere koji komentari će biti vidljivi na stranici svakog prodavca, tako što je
za svaki od komentara dostupna opcija Arhiviraj, čijim izborom taj komentar više neće biti
prikazivan na stranici. Prikaz komentara realizovati kao zasebnu aktivnost do koje korisnik
dolazi tako što na stranici svakog prodavca iz Toolbar menija bira opciju Komentari.

Rad sa artiklima
Obezbediti prodavcima prikaz svih njihovih artikala u okviru zasebne aktivnosti za prikaz artikala
(korisnik dolazi do ove aktivnosti preko izbora opcije Artikli na Toolbaru koji se nalazi na stranici
svakog prodavca). U okviru ovog prikaza prodavac ima mogućnost da odabere neku od opcija -
ažuriranje ili brisanje pojedinačnih artikala. Klikom na dugme za brisanje artikla se isti uklanja sa
prikaza svih artikala, dok klikom na dugme za ažuriranje artikla prodavac biva preusmeren na
posebnu aktivnost za ažuriranje istog. Ažuriranje pojedinačnog artikla realizovati kroz
mehanizam popunjene forme, gde je svaki od podataka artikla moguće izmeniti.
Prodavci imaju mogućnost dodavanja novih artikala kroz zasebnu aktivnost, gde se unose svi
podaci vezani za artikle, uključujući i izbor slike samog artikla (opciju za prelaz na aktivnost za
dodavanje novog artikla implementirati kao dugme na stranici za prikaz svih artikala). Nakon
dodavanja novog artikla, vratiti prodavca na prikaz svih njegovih artikala.

Akcije
Kroz zasebnu aktivnost (dostupnu prodavcima kroz Toolbar meni na glavnom prikazu)
omogućiti prikaz svih aktuelnih akcija jednog prodavca (aktuelne akcije su one čije vreme
trajanja nije isteklo u odnosu na trenutni momenat, a prikazuju se i one gde vreme početka
trajanja akcije tek predstoji). Dodati dugme za kreiranje nove akcije, gde klikom na isto
prodavac biva preusmeren na formu za unos akcije. U okviru forme, pored trajanja akcije (izbor
početnog i krajnjeg datuma), prodavac unosi opis aktivnosti i bira na koje artikle se akcija
odnosi. Nakon uspešnog dodavanja akcije prodavac se vraća na prikaz svih akcija.

Poručivanje
Kreirati zasebnu aktivnost u okviru koje kupci imaju mogućnost poručivanja artikala putem
aplikacije. Naručivanje se odvija kroz nekoliko koraka.
1. U okviru glavnog prikaza potrebno je odabrati konkretnog prodavca, pri čemu se
prodavci prikazuju kroz list view.
2. Nakon izbora konkretnog prodavca prelazi se na stranicu izabranog prodavca odakle se
može pristupiti naručivanju njegovih konkretnih artikala.
3. Korisnik bira artikle koje želi da poruči kao i količinu istih. Prilikom poručivanja artikala,
kupac može u okviru jedne porudžbine da naruči artikle samo jednog prodavca.
4. Izborom dugmeta “Završi kupovinu” korisniku se prikazuju detalji - sve izabrane stavke
(artikal, količina, cena svakog od artikala), kao i ukupna cena cele porudžbine.
5. Klikom na dugme “Poruči”, korisnik završava naručivanje i biva preusmeren glavni
prikaz.
Kod prikaza artikala voditi računa da li se isti nalaze na akciji te prikazivati i akcijske ponude.
Recenzije
Nakon isporučene porudžbine, kupac ima mogućnost ostavljanja recenzije. U okviru zasebne
aktivnosti prikazati kupcu sve prispele i neocenjene porudžbine (dolazak kupca na aktivnost za
prikaz ovih porudžbina omogućiti kroz opciju Toolbara na glavnom prikazu). Izborom jedne
konkretne porudžbine, kupac ima mogućnost ostavljanja komentara u slobodnoj formi kao i
ocene (1-5) pri čemu su oba podatka (i komentar i ocena) obavezni. Takođe, korisnik može da
odabere da ostane anoniman (štikliranjem checkbox-a). U slučaju da je korisnik izabrao da
ostane anoniman, njegovi podaci (ime, prezime i/ili korisničko ime) neće biti prikazani na stranici
svih komentara jednog prodavca.
Na osnovu ocene svake porudžbine formira se prosečna ocena svakog prodavca koja se
prikazuje u okviru glavnog prikaza pored podataka svakog od prodavaca, kao i na stranici
svakog zasebnog prodavca.

Rad sa korisnicima
Administratori imaju mogućnost pregleda svih korisnika, kao i blokiranja pojedinačnih korisnika.
Blokirani korisnici nemaju pristup aplikaciji (onemogućiti njihovu prijavu na sistem). Aktivnosti za
prikaz svih korisnika administratori pristupaju izborom opcije Korisnici u okviru Toolbara na
glavnom prikazu.