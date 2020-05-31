INSERT INTO "role" ("id_role", "nama_role") VALUES
(1, 'Administrator'),
(2, 'Supervisor'),
(3, 'Manajer Operational Risk'),
(4, 'QA Lead Operational Risk'),
(5, 'QA Officer Operational Risk'),
(6, 'Branch Manager'),
(7, 'Super QA Officer Operational Risk');

INSERT INTO "access_permissions" ("akses_dashboard_staff","akses_bukti_pelaksanaan", "akses_hapus_risiko", "akses_persetujuan_bukti_pelaksanaan", "akses_rekomendasi", "akses_risiko", "akses_risk_level", "akses_risk_rating", "akses_tabel_rekomendasi", "akses_tabel_risiko", "akses_tambah_bukti_pelaksanaan", "akses_tambah_risiko", "akses_ubah_bukti_pelaksanaan", "akses_ubah_hierarki", "akses_ubah_risiko", "atur_tenggat_waktu", "ubah_reminder", "ubah_risk_level", "ubah_risk_rating", "role_id_role")
VALUES
(FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, TRUE, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, '1'),
(TRUE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE, TRUE, FALSE, FALSE, FALSE, TRUE, TRUE, '2'),
(FALSE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE, TRUE, TRUE, '3'),
(FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, '4'),
(FALSE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, TRUE, FALSE, FALSE, '5'),
(FALSE, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, '6'),
(FALSE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, '7');

INSERT INTO "reminder_template" ("id_reminder_template", "body", "global", "subjects") 
VALUES ('1', 'The global template for your email', TRUE, 'A Reminder for Recommendation Assignment');

INSERT INTO "status_bukti_pelaksanaan" ("id_status_bukti", "keterangan_status", "nama_status") 
VALUES 
('1', NULL, 'Menunggu Persetujuan'), 
('2', NULL, 'Disetujui'), 
('3', NULL, 'Ditolak');

INSERT INTO "status_rencana_pemeriksaan" ("id_status_rencana", "keterangan_status", "nama_status")
VALUES 
('1', NULL, 'Draft'), 
('2', NULL, 'Sedang Dijalankan'), 
('3', NULL, 'Selesai');

INSERT INTO "status_hasil_pemeriksaan" ("id_status_hasil", "keterangan_status", "nama_status") 
VALUES 
('1', NULL, 'Draft'), 
('2', NULL, 'Menunggu Persetujuan'), 
('3', NULL, 'Ditolak'), 
('4', NULL, 'Menunggu Pelaksanaan'), 
('5', NULL, 'Selesai');

INSERT INTO "status_rekomendasi" ("id_status_rekomendasi", "dapat_set_tenggat_waktu", "keterangan_status", "nama_status") 
VALUES 
('1', FALSE, NULL, 'Draft'), 
('2', FALSE, NULL, 'Menunggu Persetujuan'), 
('3', FALSE, NULL, 'Ditolak'), 
('4', TRUE, NULL, 'Menunggu Pengaturan Tenggat Waktu'), 
('5', TRUE, NULL, 'Menunggu Pelaksanaan'), 
('6', FALSE, NULL, 'Sedang Dilaksanakan'), 
('7', FALSE, NULL, 'Selesai');


INSERT INTO "employee" ("id_employee", "email", "jabatan", "nama", "no_hp", "password", "status", "username", "reminder_template", "role") 
VALUES 
('1', 'administrator@sirio.com', 'Administrator', 'Aku Administrator', '0123', '', 'AKTIF', 'administrator', '1', '1'),
('2', 'supervisor@sirio.com', 'Supervisor', 'Aku Supervisor', '0123', '', 'AKTIF', 'supervisor', '1', '2'),
('3', 'manajer@sirio.com', 'QA Manajer', 'Aku Manajer', '0123', '', 'AKTIF', 'manajer', '1', '3'),
('4', 'lead@sirio.com', 'QA Lead', 'Aku Lead', '0123', '', 'AKTIF', 'leadlead', '1', '4'),
('5', 'officer@sirio.com', 'QA Officer', 'Aku Officer', '0123', '', 'AKTIF', 'qaofficer', '1', '5'),
('6', 'bm@sirio.com', 'Branch Manager', 'Aku BM', '0123', '', 'AKTIF', 'branchmanager', '1', '6'),
('7', 'superQA@sirio.com', 'Super QA', 'Aku SuperQA', '0123', '', 'AKTIF', 'superofficer', '1', '7');


INSERT INTO "sop" ("id_sop", "judul", "kategori", "link_dokumen", "status", "pembuat") VALUES ('1', 'test', 'test', 'https://test', 'AKTIF', '1');

INSERT INTO "risiko" ("id_risiko", "deskripsi", "detail_uraian", "ketentuan_sampel", "metodologi", "nama_risiko", "risiko_kategori", "status", "parent", "sop") 
VALUES 
('1', NULL, NULL, NULL, NULL, 'Administrasi', '1', 'AKTIF', NULL, '1'),
('2', NULL, NULL, NULL, NULL, 'Operasional Point', '2', 'AKTIF', '1', '1'),
('3', 
'QA Team melakukan pengecekan:
1. Kebersihan dan kerapihan Point
2. Mencuci peralatan dapur/ makan setelah digunakan
3. Tidak membiarkan tempat sampah di dalam rumah penuh
4. Tidak merokok di dalam rumah/ kantor dan tidak membuang puntung rokok sembarangan di luar lingkungan rumah', 
'Kebersihan Point meliputi:
1. Ruang Briefing
2. Ruang Teller
3. Ruang Arsip
4. Ruang Dapur
5. Kamar Mandi
6. Kamar Tidur',
NULL,
'Observasi', 'Kebersihan Point', '3', 'AKTIF', '2', '1'),
('4', 
'QA Team melakukan pengecekan kelengkapan dan jumlah inventaris sesuai dengan SOP',
'Kelengkapan inventaris meliputi:
1. Inventaris utama
2. Inventaris elektronik
3. Inventaris tambahan', 
NULL,
'Observasi', 'Kelengkapan Inventaris', '3', 'AKTIF', '2', '1'),
('5', 
'QA Team melakukan pengecekan:
1. Melaksanakan briefing pagi secara disiplin dan tepat waktu pukul 08.00
2. Mengisi Form Branch Daily Planning (BDP)
3. Mendiskusikan bersama BM terkait planning di hari tersebut
4. Melakukan evaluasi kegiatan kemarin pada buku agenda harian
5. Memberikan laporan progress hasil suprise visit BM dan SPV
6. Melaporkan progress pengarsipan dokumen mitra yang telah cair',
'Briefing pagi meliputi:
1. Disiplin waktu
2. Branch Daily Planning
3. Evaluasi Agenda Harian Point
4. Rule Briefing', 
NULL,
'Observasi', 'Briefing Pagi', '3', 'AKTIF', '2', '1'),
('6', 
'QA Team melakukan pengecekan:
1. BP/ SPV mengisi laporan kegiatan buku agenda harian Point dan pengecekan oleh BM
2. BP melakukan pencatatan buku In & Out DAN pengecekan oleh BM
3. BP mengisi buku agenda BP, BM cek min. 1x seminggu, AM cek min. 1x sebulan
4. BP mengisi buku titipan brankas, BP dan Teller paraf, AM cek min. 1x sebulan
5. AM mengisi buku monitoring AM,  BM menindaklanjuti komentar AM
6. Buku tamu diisi dan AM wajib memeriksa buku tamu
7.  BM dan BP mengisi buku meeting weekly point, AM wajib memeriksa
8. BP mengisi buku TR, BM dan AM memeriksa
9. BP mengisi buku PAR, BM dan AM memeriksa',
'Pembukuan Point meliputi:
1. Buku agenda harian Point
2. Buku In and Out
3. Agenda BP
4. Buku titipan uang brankas
5. Buku monitoring AM
6. Buku tamu
7. Buku meeting weekly Point
8. Buku Tanggung Renteng
9. Buku PAR', 
'Min. 7 sampel atau 5% dari populasi (mengikuti ketentuan mana yang paling kecil) untuk masing-masing item',
'Sampling Review', 'Pembukuan Point', '3', 'AKTIF', '1', '1'),
('7', 
'QA Team melakukan pengecekan:
1. Buku/ form ada dan diarsip rapi
2. Buku/ form terisi sesuai ketentuan',
'Pengarsipan meliputi:
1. Pengarsipan data pembiayaan mitra
2. Pengarsipan form surprise visit BM
3. Closing harian Point
4. Validasi teller MIS
5. Rekap kas
6. Buku brankas
7. Slip setor dan slip tarik
8. Cash opname', 
'Min. 7 sampel atau 5% dari populasi (mengikuti ketentuan mana yang paling kecil) untuk masing-masing item',
'Sampling Review', 'Pengarsipan', '3', 'AKTIF', '1', '1'),
('8', 
'QA Team melakukan pengecekan:
1. Form tersedia di Point
2. Form diisi lengkap sesuai dengan ketentuan',
'Form pembiayaan meliputi:
1. Form sosialisasi
2. Form komite lapang
3. Form permohonan pembiayaan dan pernyataan penanggung jawab
4. BA komite
5. Form LWK/ UPK
6. Form home visit
7. Form rekap keuangan majelis
8. Form mitra cuti
9. Form pengunduran diri mitra
10. Form waiver
11. Form tanggung renteng
12. Form rincian tanggung renteng
13. Form SP 1-3
14. Form Branch Daily Planning (BDP)
15. Form pembelian barang ', 
'Min. 7 sampel atau 5% dari populasi (mengikuti ketentuan mana yang paling kecil) untuk masing-masing item',
'Sampling Review', 'Form Pembiayaan', '3', 'AKTIF', '1', '1'),
('9', 
'QA Team melakukan pengecekan:
1. Ada/ tidaknya papan monitoring
2. Informasi yang ada di papan monitoring update/ tidak',
'Papan monitoring meliputi:
1. Papan jadwal pelayanan majelis
2. Papan pencapaian target
3. Papan progress report
4. Papan daftar proses mitra
5. Papan mapping wilayah', 
NULL,
'Observasi', 'Papan Monitoring', '3', 'AKTIF', '1', '1'),
('10', 
'QA Team melakukan pengecekan:
1. Ketersediaan kelengkapan point seperti SOP & JukNis, LWK toolkit, ID card dan seragam, serta flyer dan kartu angsuran
2. Kecukupan stock kelengkapan point',
'Kelengkapan Point meliputi:
1. SOP+Juknis
2. LWK toolkit
3. ID card dan seragam
4. Flyer dan kartu angsuran', 
NULL,
'Observasi', 'Kelengkapan Point', '3', 'AKTIF', '1', '1'),
('11', 
'QA Team melakukan pengecekan berikut:
A. Sosialisasi
1. BP menyampaikan tentang Amartha, produk, dst
2. BP mengisi form sosialisasi
3. BP melakukan follow up sosialisasi max. 1 minggu setelahnya
4. BM mengirim laporan mingguan ke AM

B. Komite Lapang
1. BP menyampaikan profil perusahaan, produk,  syarat menjadi mitra, hak dan kewajiban, serta proses pembiayaan di rumah salah satu calon mitra
2. BP mengisi form komite lapang
3. BP melakukan pengecekan absense
4. BP melakukan mengecekan kelengkapan dokumen calon mitra

C. Uji Kelayakan
1. BP melakukan proses UK maks. 2 hari setelah Komite Lapang
2. BP memastikan kelengkapan dokumen calon mitra dan kesediaan PJ di rumah calon mitra
3. BP memastikan form pembiayaan terisi dengan lengkap
4. BP melakukan cek lingkungan
5. BP melakukan verifikasi dan mencocokan copy dokumen
6. BP menlengkapi data UK
7. BM melakukan validasi data UK
8. QA melakukan re-UK

D. Komite Pembiayaan
1. BP mengisi form BA secara lengkap
2. BM melakukan validasi data Mitra dan persyaratan dokumen

E. LWK
1. BP melakukan LWK dalam 2 hari berturut-turut
2. menyampaikan materi LWK sesuai modul
3. BP mengisi form absensi
4. BP memastikan mitra mengisi form pembiayaan mitra

F. UPK
1. Mitra telah mengikuti LWK selama 2 hari berturut-turut
2. BM melakukan verifikasi sebelum UPK
3. BP memastikan form TR diisi oleh mitra

G. Pengajuan
1. BP menyampaikan nama mitra dan PJ, nominal pinjaman, persetujuan seluruh mitra, dan komitmen TR

H. Pencairan
1. Pencairan dilakukan di rumah mitra disaksikan oleh BM/ SPV dan PJ
2. BP menyampaikan nominal pinjaman, tujuan, dan margin
3. Mitra menyampaikan dan menyetujui nominal pinjaman, tujuan, dan margin
4. Form akad dilengkapi',
'Proses meliputi:
1. Sosialisasi
2. Komite lapang
3. Uji kelayakan
4. Komite pembiayaan
5. Latihan wajib kelompok
6. Uji pengesahan kelompok
7. Pengajuan
8. Pencairan', 
'Min. 7 sampel atau 5% dari populasi (mengikuti ketentuan mana yang paling kecil) untuk masing-masing item yang disesuaikan dengan jadwal BP

Min. 10 sampel atau 5% dari populasi (mengikuti ketentuan mana yang paling kecil) untuk re-UK oleh QA team',
'Sampling Review dan Wawancara', 'Proses', '3', 'AKTIF', NULL, '1'),
('12', 
'QA Team melakukan pengecekan berikut:
1. Pelayanan dilakukan rutin secara mingguan di rumah mitra
2. Pelayanan dilakukan dengan juga memberikan pendampingan
3. BP datang tepat waktu
4. Kehadiran mitra tepat waktu. 10 menit telat tanpa informasi jelas dianggap tidak hadir
5. BP wajib home visit kepada mitra yang tidak hadir
6. Mitra duduk sesuai topsheet dan dibuka dengan ikrar
7. BM memberikan solusi pelayanan kepada majelis dengan tingkat ketidakhadiran mitra 40%
8.  BP mengucapkan ikrar BP
9. BP menyampaikan hak dan kewajiban, serta ketentuan mitra
10. Pengumpulan kartu angsuran rapi
11. BP mencatat transaksi di kartu angsuran dan menyamakan data pembayaran mitra
12. BP meminta ketua majelis untuk validasi data di topsheet
13. kesesuaian data topsheet dan kartu angsuran',
'Proses pelayanan',
'Min. 7 sampel atau 5% dari populasi (mengikuti ketentuan mana yang paling kecil) untuk masing-masing item yang disesuaikan dengan jadwal BP',
'Sampling Review', 'Pelayanan', '3', 'AKTIF', NULL, '1'),
('13', 
'QA Team melakukan pengecekan berikut:

Surprise Visit
1. BM/ SPV melakukan SV min. 2 majelis per hari
2. BM/ SPV  mengisi form SV
3. BM/ SPV melakukan pengecekan proses pelayanan dan melakukan kunjungan ke rumah mitra
4. BM/ SPV memberikan feedback surprise visit kepada BP

Meeting Evaluasi Weekly
1. BM melakukan evaluasi secara rutin mingguan
2. BM share pencapaian target dan staff share masalah yang dihadapi
3. BM mencatat hasil evaluasi
4. BM/ SPV/ BP senior memberikan refreshment/ training

Pemetaan Wilayah
1. BM/ SPV melakukan pemetaan wilayah sesuai data yang dikirimkan BE
2. Pembukaan kecamatan max. 4 kecamatan dalam 1 Point

Kartu Angsuran
1. BP memastikan kelengkapan pengisian data pada kartu angsuran
2. BM/ SPV memberikan paraf pada kartu angsuran saat SV sebagai bukti kartu angsuran sudah dicek

Arsip Data Mitra
1. BP/ BM/ SPV memastikan kelengkapan dokumen untuk arsip data mitra',
'Monitoring & Controlling meliputi:
1. Surprise visit
2. Meeting evaluasi weekly
3. Pemetaan wilayah
4. Kartu angsuran
5. Random sampling arsip data mitra', 
'Min. 7 sampel atau 5% dari populasi (mengikuti ketentuan mana yang paling kecil) untuk masing-masing item',
'Sampling Review', 'Monitoring & Controlling', '3', 'AKTIF', NULL, '1');

INSERT INTO "risk_level" ("id_level", "bobot_level", "keterangan_level", "nama_level", "status", "pengelola") 
VALUES 
('1', '-4', 'High Risk', 'High Risk', 'AKTIF', '1'),
('2', '-2', 'Medium Risk', 'Medium Risk', 'AKTIF', '1'),
('3', '-1', 'Low Risk', 'Low Risk', 'AKTIF', '1'),
('4', '0', 'No Risk', 'No Risk', 'AKTIF', '1');

INSERT INTO "risk_rating" ("id_rating", "keterangan_rating", "nama_rating", "skor_maksimal", "skor_minimal", "pengelola") VALUES ('1', 'Bagus', 'Bagus', '50', '25', '1');

INSERT INTO "kantor_cabang" ("id_kantor", "area", "kunjungan_audit", "nama_kantor", "regional", "status", "pembuat", "pemilik", "risk_rating") VALUES 
('1', 'Lampung Utara', FALSE, 'Abung Selatan', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('2', 'Cilacap', FALSE, 'Adipala', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('3', 'Tegal', FALSE, 'Adiwerna', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('4', 'Asahan', FALSE, 'Air Joman', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('5', 'Batu Bara', FALSE, 'Air Putih', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('6', 'Banyumas', FALSE, 'Ajibarang', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('7', 'Semarang', FALSE, 'Ambarawa', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('8', 'Agam', FALSE, 'Ampe Angke', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('9', 'Cirebon', FALSE, 'Arjawinangun', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('10', 'Situbondo', FALSE, 'Asembagus', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('11', 'Cirebon', TRUE, 'Astanajapura', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('12', 'Cirebon', FALSE, 'Babakan', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('13', 'Gowa', FALSE, 'Bajeng Barat', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('14', 'Donggala', FALSE, 'Balaesang', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('15', 'Yogyakarta', FALSE, 'Bambanglipuro', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('16', 'Palu', FALSE, 'Banawa', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('17', 'Tuban', FALSE, 'Bancar', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('18', 'Pekalongan', FALSE, 'Bandar', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('19', 'Simalungun', FALSE, 'Bandar Simalungun', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('20', 'Lampung Timur', FALSE, 'Bandar Sribawono', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('21', 'Magelang', FALSE, 'Bandongan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('22', 'Tulungagung', FALSE, 'Bandung', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('23', 'Polman', FALSE, 'Banggae', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('24', 'Pasuruan', FALSE, 'Bangil', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('25', 'Pekanbaru', FALSE, 'Bangkinang Kota', 'Riau', 'AKTIF', '1', '1', '1'),
('26', 'Banyuwangi', FALSE, 'Bangorejo', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('27', 'Mojokerto', FALSE, 'Bangsal', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('28', 'Yogyakarta', TRUE, 'Banguntapan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('29', 'Tulang Bawang', FALSE, 'Banjar Agung', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('30', 'Tulang Bawang', FALSE, 'Banjar Margo', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('31', 'Brebes', FALSE, 'Banjarharjo', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('32', 'Solo', FALSE, 'Banjarsari', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('33', 'Ciamis', FALSE, 'Banjarsari 2', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('34', 'Jeneponto', FALSE, 'Bantaeng', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('35', 'Agam', FALSE, 'Banuhampu', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('36', 'Palembang', FALSE, 'Banyuasin I', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('37', 'Banyuasin', FALSE, 'Banyuasin III', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('38', 'Banyumas', FALSE, 'Banyumas', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('39', 'Tolitoli', FALSE, 'Baolan', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('40', 'Gowa', FALSE, 'Barombong', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('41', 'Serang', FALSE, 'Baros', 'Banten', 'AKTIF', '1', '1', '1'),
('42', 'Palopo', FALSE, 'Barra', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('43', 'Agam', FALSE, 'Baso', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('44', 'Lampung Timur', FALSE, 'Batanghari', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('45', 'Baubau', FALSE, 'Batupoaro', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('46', 'Ogan Komering Ulu', FALSE, 'Baturaja Timur', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('47', 'Wonogiri', FALSE, 'Baturetno', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('48', 'Pemalang', FALSE, 'Belik', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('49', 'Ogan Komering Ulu', FALSE, 'Belitang', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('50', 'Gresik', FALSE, 'Benjeng', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('51', 'Nganjuk', TRUE, 'Berbek', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('52', 'Deli Serdang', FALSE, 'Beringin', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('53', 'Bondowoso', FALSE, 'Besuki', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('54', 'Jeneponto', FALSE, 'Binamu', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('55', 'Kota Medan', FALSE, 'Binjai Timur', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('56', 'Palu', FALSE, 'Biromaru', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('57', 'Malang', FALSE, 'Blimbing', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('58', 'Purwakarta', FALSE, 'Bojong', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('59', 'Tegal', FALSE, 'Bojong 2', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('60', 'Cianjur', FALSE, 'Bojongpicung', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('61', 'Bondowoso', FALSE, 'Bondowoso', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('62', 'Palopo', FALSE, 'Bonebone', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('63', 'Gowa', FALSE, 'Bontomarannu', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('64', 'Gowa', FALSE, 'Bontonompo', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('65', 'Bojonegoro', FALSE, 'Boureno', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('66', 'Tulungagung', FALSE, 'Boyolangu', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('67', 'Sumedang', FALSE, 'Buahdua', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('68', 'Partner', FALSE, 'Bukalapak', 'Partner', 'AKTIF', '1', '1', '1'),
('69', 'Lampung Utara', FALSE, 'Bukit Kemuning', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('70', 'Brebes', TRUE, 'Bulakamba', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('71', 'Brebes', TRUE, 'Bumiayu', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('72', 'Tegal', FALSE, 'Bumijawa', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('73', 'Subang', TRUE, 'Bunihayu', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('74', 'Karawang', FALSE, 'Cabangbungin', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('75', 'Cianjur', FALSE, 'Campaka', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('76', 'Polman', FALSE, 'Campalagian', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('77', 'Bandung', TRUE, 'Cangkuang', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('78', 'Klaten', FALSE, 'Cawas', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('79', 'Gresik', FALSE, 'Cerme', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('80', 'Kuningan', FALSE, 'Ciawigebang', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('81', 'Sukabumi', TRUE, 'Cibadak', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('82', 'Lebak', FALSE, 'Cibeber', 'Banten', 'AKTIF', '1', '1', '1'),
('83', 'Sukabumi', FALSE, 'Cicurug', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('84', 'Majalengka', TRUE, 'Cigasong', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('85', 'Ciamis', FALSE, 'Cijeungjing', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('86', 'Cianjur', FALSE, 'Cikalong Kulon', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('87', 'Serang', FALSE, 'Cikande', 'Banten', 'AKTIF', '1', '1', '1'),
('88', 'Lebak', FALSE, 'Cikulur', 'Banten', 'AKTIF', '1', '1', '1'),
('89', 'Cilacap', FALSE, 'Cilacap Selatan', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('90', 'Cianjur', FALSE, 'Cilaku', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('91', 'Karawang', FALSE, 'Cilamaya Wetan', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('92', 'Kuningan', FALSE, 'Cilimus', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('93', 'Pandeglang', FALSE, 'Cimanuk', 'Banten', 'AKTIF', '1', '1', '1'),
('94', 'Lebak', FALSE, 'Cipanas', 'Banten', 'AKTIF', '1', '1', '1'),
('95', 'Bandung', FALSE, 'Ciparay', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('96', 'Serang', TRUE, 'Ciruas', 'Banten', 'AKTIF', '1', '1', '1'),
('97', 'Sukabumi', FALSE, 'Cisaat', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('98', 'Bogor', FALSE, 'Ciseeng', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('99', 'Sumedang', FALSE, 'Cisitu', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('100', 'Tangerang', FALSE, 'Cisoka', 'Banten', 'AKTIF', '1', '1', '1'),
('101', 'Serang', FALSE, 'Ciwandan', 'Banten', 'AKTIF', '1', '1', '1'),
('102', 'Banyuwangi', FALSE, 'Cluring', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('103', 'Sumedang', FALSE, 'Conggeang', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('104', 'Tolitoli', FALSE, 'Dampal Selatan', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('105', 'Asahan', FALSE, 'Datuk Bandar Timur', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('106', 'Klaten', FALSE, 'Delanggu', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('107', 'Jombang', FALSE, 'Diwek', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('108', 'Madiun', FALSE, 'Dolopo', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('109', 'Tolitoli', FALSE, 'Dondo', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('110', 'Probolinggo', FALSE, 'Dringgu', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('111', 'Dumai', FALSE, 'Dumai Selatan', '', 'AKTIF', '1', '1', '1'),
('112', 'Blora', FALSE, 'Gabus', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('113', 'Pesawaran', FALSE, 'Gading Rejo', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('114', 'Tolitoli', FALSE, 'Galang', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('115', 'Takalar', FALSE, 'Galesong', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('116', 'Takalar', FALSE, 'Galesong Selatan', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('117', 'Tulungagung', FALSE, 'Gandusari', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('118', 'Blitar', FALSE, 'Garum', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('119', 'Sidoarjo', FALSE, 'Gedangan', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('120', 'Pesawaran', FALSE, 'Gedong Tataan', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('121', 'Ogan Ilir', FALSE, 'Gelumbang', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('122', 'Solo', FALSE, 'Gemolong', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('123', 'Banyuwangi', FALSE, 'Genteng', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('124', 'Tanggamus', FALSE, 'Gisting', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('125', 'Kebumen', FALSE, 'Gombong', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('126', 'Solo', FALSE, 'Gondang', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('127', 'Nganjuk', FALSE, 'Gondang 2', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('128', 'Magelang', FALSE, 'Grabag', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('129', 'Grobogan', FALSE, 'Gubug', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('130', 'Jombang', FALSE, 'Gudo', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('131', 'Payakumbuh', FALSE, 'Guguak', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('132', 'Solok', FALSE, 'Gunung Talang', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('133', 'Payakumbuh', FALSE, 'Harau', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('134', 'Subang', FALSE, 'Haurgeulis', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('135', 'Palembang', FALSE, 'Ilir Barat I', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('136', 'Ogan Ilir', FALSE, 'Indralaya', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('137', 'Indramayu', FALSE, 'Indramayu', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('138', 'Kota Jambi', FALSE, 'Jambi Timur', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('139', 'Bogor', FALSE, 'Jasinga', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('140', 'Indramayu', FALSE, 'Jatibarang', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('141', 'Brebes', FALSE, 'Jatibarang 2', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('142', 'Klaten', FALSE, 'Jatinom', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('143', 'Wonogiri', FALSE, 'Jatisrono', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('144', 'Ponorogo', FALSE, 'Jenangan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('145', 'Jember', TRUE, 'Jenggawah', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('146', 'Blora', FALSE, 'Jepon', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('147', 'Cilacap', TRUE, 'Jeruklegi', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('148', 'Madiun', FALSE, 'Jiwan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('149', 'Klaten', FALSE, 'Jogonalan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('150', 'Jombang', FALSE, 'Jombang', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('151', 'Pesisir Selatan', FALSE, 'Jurai', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('152', 'Banyuwangi', FALSE, 'Kabat', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('153', 'Kendari', FALSE, 'Kadia', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('154', 'Pekalongan', FALSE, 'Kajen', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('155', 'Lampung Selatan', FALSE, 'Kalianda', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('156', 'Palembang', FALSE, 'Kalidoni', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('157', 'Banjarnegara', FALSE, 'Kaligonda', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('158', 'Banyuwangi', FALSE, 'Kalipuro', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('159', 'Lampung Tengah', FALSE, 'Kalirejo', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('160', 'Jember', FALSE, 'Kalisat', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('161', 'Bojonegoro', FALSE, 'Kalitidu', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('162', 'Indramayu', FALSE, 'Kandang Haur', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('163', 'Blitar', FALSE, 'Kanigoro', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('164', 'Bojonegoro', FALSE, 'Kapas', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('165', 'Cianjur', FALSE, 'Karang Tengah', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('166', 'Solo', FALSE, 'Karanganyar', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('167', 'Pekalongan', FALSE, 'Karanganyar 2', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('168', 'Grobogan', FALSE, 'Karangawen', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('169', 'Lamongan', FALSE, 'Karanggeneng', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('170', 'Ngawi', FALSE, 'Karangjati', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('171', 'Banyumas', FALSE, 'Karanglewas', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('172', 'Parigi Mountong', FALSE, 'Kasimbar', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('173', 'Ogan Komering Ilir', FALSE, 'Kayu Agung', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('174', 'Kebumen', FALSE, 'Kebumen', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('175', 'Bandar Lampung', FALSE, 'Kedaton', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('176', 'Pesawaran', FALSE, 'Kedondong', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('177', 'Lamongan', FALSE, 'Kedungpring', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('178', 'Tulungagung', FALSE, 'Kedungwaru', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('179', 'Pekalongan', TRUE, 'Kedungwuni', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('180', 'Banjarnegara', FALSE, 'Kejobong', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('181', 'Bogor', FALSE, 'Kemang', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('182', 'Bandar Lampung', FALSE, 'Kemiling', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('183', 'Purworejo', FALSE, 'Kemiri', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('184', 'Mojokerto', FALSE, 'Kemlagi', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('185', 'Jember', TRUE, 'Kencong', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('186', 'Kendari', FALSE, 'Kendari Barat', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('187', 'Malang', TRUE, 'Kepanjen', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('188', 'Nganjuk', TRUE, 'Kertosono', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('189', 'Pasaman Barat', FALSE, 'Kinali', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('190', 'Asahan', FALSE, 'Kisaran Barat', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('191', 'Karawang', TRUE, 'Klari', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('192', 'Klaten', FALSE, 'Klaten Selatan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('193', 'Klaten', FALSE, 'Klaten Utara', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('194', 'Baubau', FALSE, 'Kokalukuna', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('195', 'Kolaka', FALSE, 'Kolaka', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('196', 'Tanggamus', FALSE, 'Kota Agung', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('197', 'Kota Jambi', FALSE, 'Kota Baru', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('198', 'Lampung Tengah', FALSE, 'Kota Gajah', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('199', 'Lampung Timur', FALSE, 'Kota Metro', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('200', 'Lampung Utara', FALSE, 'Kotabumi', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('201', 'Padang', FALSE, 'Koto Tangah', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('202', 'Grobogan', FALSE, 'Kradenan', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('203', 'Serang', TRUE, 'Kramatwatu', 'Banten', 'AKTIF', '1', '1', '1'),
('204', 'Mojokerto', FALSE, 'Kranggan', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('205', 'Sidoarjo', TRUE, 'Krian', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('206', 'Tangerang', FALSE, 'Kronjo', 'Banten', 'AKTIF', '1', '1', '1'),
('207', 'Kuningan', FALSE, 'Kuningan', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('208', 'Padang', FALSE, 'Kuranji', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('209', 'Banjarnegara', FALSE, 'Kutasari', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('210', 'Purworejo', TRUE, 'Kutoarjo', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('211', 'Pangkajene', FALSE, 'Labakkang', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('212', 'Pandeglang', FALSE, 'Labuan', 'Banten', 'AKTIF', '1', '1', '1'),
('213', 'Donggala', FALSE, 'Labuan 2', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('214', 'Kendari', FALSE, 'Laeya', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('215', 'Poso', FALSE, 'Lage', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('216', 'Lahat', FALSE, 'Lahat', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('217', 'Bone', FALSE, 'Lalabata', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('218', 'Palopo', FALSE, 'Lamasi', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('219', 'Lamongan', FALSE, 'Lamongan', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('220', 'Ciamis', FALSE, 'Langensari', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('221', 'Payakumbuh', FALSE, 'Lareh', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('222', 'Kolaka', FALSE, 'Lasusua', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('223', 'Tegal', FALSE, 'Lebaksiu', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('224', 'Tangerang', TRUE, 'Legok', 'Banten', 'AKTIF', '1', '1', '1'),
('225', 'Majalengka', FALSE, 'Lemahsugih', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('226', 'Solok', FALSE, 'Lembah Gumanti', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('227', 'Pasaman Barat', FALSE, 'Lembah Melintang', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('228', 'Ogan Komering Ilir', FALSE, 'Lempuing', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('229', 'Bogor', TRUE, 'Leuwiliang', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('230', 'Majalengka', FALSE, 'Leuwimunding', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('231', 'Majalengka', FALSE, 'Ligung', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('232', 'Solok', FALSE, 'Lima Kaum', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('233', 'Batu Bara', FALSE, 'Lima Puluh', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('234', 'Purworejo', FALSE, 'Loano', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('235', 'Nganjuk', FALSE, 'Loceret', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('236', 'Konawe', FALSE, 'Loea', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('237', 'Pariaman', FALSE, 'Lubuk Alung', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('238', 'Agam', FALSE, 'Lubuk Basung', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('239', 'Padang', FALSE, 'Lubuk Begalung', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('240', 'Ogan Komering Ulu', FALSE, 'Lubuk Raja', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('241', 'Solok', FALSE, 'Lubuk Sikarah', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('242', 'Lubuklinggau', FALSE, 'Lubuklinggau Barat I', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('243', 'Lubuklinggau', FALSE, 'Lubuklinggau Timur I', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('244', 'Kuningan', FALSE, 'Luragung', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('245', 'Madiun', FALSE, 'Madiun', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('246', 'Pandeglang', FALSE, 'Majasari', 'Banten', 'AKTIF', '1', '1', '1'),
('247', 'Cilacap', TRUE, 'Majenang', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('248', 'Kuningan', FALSE, 'Maleber', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('249', 'Lebak', FALSE, 'Malingping', 'Banten', 'AKTIF', '1', '1', '1'),
('250', 'Mamuju', FALSE, 'Mamuju', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('251', 'Banjarnegara', FALSE, 'Mandiraja', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('252', 'Takalar', FALSE, 'Mangarabombang', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('253', 'Makassar', FALSE, 'Manggala', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('254', 'Palu', FALSE, 'Mantikulore', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('255', 'Lamongan', FALSE, 'Mantup', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('256', 'Gresik', FALSE, 'Manyar', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('257', 'Magetan', FALSE, 'Maospati', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('258', 'Tegal', FALSE, 'Margasari', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('259', 'Makassar', FALSE, 'Mariso', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('260', 'Probolinggo', FALSE, 'Maron', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('261', 'Pekanbaru', FALSE, 'Marpoyan Damai', 'Riau', 'AKTIF', '1', '1', '1'),
('262', 'Ogan Komering Ulu', FALSE, 'Martapura', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('263', 'Palopo', FALSE, 'Masamba', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('264', 'Solo', FALSE, 'Masaran', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('265', 'Tangerang', FALSE, 'Mauk', 'Banten', 'AKTIF', '1', '1', '1'),
('266', 'Jepara', FALSE, 'Mayong', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('267', 'Kota Medan', FALSE, 'Medan Marelan', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('268', 'Kota Medan', FALSE, 'Medan Sunggal', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('269', 'Kota Medan', FALSE, 'Medan Timur', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('270', 'Lubuklinggau', FALSE, 'Megang Sakti', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('271', 'Madiun', TRUE, 'Mejayan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('272', 'Pandeglang', FALSE, 'Menes', 'Banten', 'AKTIF', '1', '1', '1'),
('273', 'Parigi Mountong', FALSE, 'Mepanga', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('274', 'Tuban', FALSE, 'Merakurak', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('275', 'Situbondo', FALSE, 'Mlandingan', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('276', 'Jepara', FALSE, 'Mlonggo', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('277', 'Mojokerto', TRUE, 'Mojo Pacet', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('278', 'Jombang', TRUE, 'Mojoagung', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('279', 'Kota Jambi', FALSE, 'Muara Bulian', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('280', 'Lahat', FALSE, 'Muara Enim', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('281', 'Magelang', FALSE, 'Mungkid', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('282', 'Sarolangun', FALSE, 'Nalo Tantan', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('283', 'Lampung Selatan', FALSE, 'Natar', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('284', 'Wonogiri', FALSE, 'Ngadirojo', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('285', 'Yogyakarta', FALSE, 'Ngaglik', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('286', 'Semarang', FALSE, 'Ngaliyan', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('287', 'Kolaka', FALSE, 'Ngapa', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('288', 'Magetan', FALSE, 'Ngariboyo', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('289', 'Kediri', TRUE, 'Ngasem', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('290', 'Blora', FALSE, 'Ngawen', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('291', 'Ngawi', FALSE, 'Ngawi', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('292', 'Lamongan', FALSE, 'Ngimbang', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('293', 'Ngawi', FALSE, 'Ngrambe', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('294', 'Nganjuk', FALSE, 'Ngronggot', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('295', 'Tulungagung', FALSE, 'Ngunut', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('296', 'Wonogiri', FALSE, 'Nguter', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('297', 'Bandung', FALSE, 'Pacet', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('298', 'Ogan Komering Ilir', FALSE, 'Padamaran', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('299', 'Serdang Bedagai', FALSE, 'Padang Hulu', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('300', 'Pariaman', FALSE, 'Padang Panjang Barat', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('301', 'Bojonegoro', FALSE, 'Padangan', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('302', 'Subang', FALSE, 'Pagaden', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('303', 'Lahat', FALSE, 'Pagaralam', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('304', 'Tanggamus', FALSE, 'Pagelaran', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('305', 'Probolinggo', FALSE, 'Paiton', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('306', 'Magelang', FALSE, 'Pakis', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('307', 'Parepare', FALSE, 'Paleteang', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('308', 'Cirebon', FALSE, 'Palimanan', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('309', 'Gowa', FALSE, 'Pallangga', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('310', 'Palu', FALSE, 'Palu Selatan', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('311', 'Sarolangun', FALSE, 'Pamenang', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('312', 'Poso', FALSE, 'Pamona Puselemba', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('313', 'Situbondo', FALSE, 'Panarukan', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('314', 'Deli Serdang', FALSE, 'Pancur Batu', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('315', 'Pasuruan', FALSE, 'Pandaan', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('316', 'Magetan', FALSE, 'Panekan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('317', 'Bandung', FALSE, 'Pangalengan', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('318', 'Pangkajene', FALSE, 'Pangkajene', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('319', 'Siak', FALSE, 'Pangkalan Kerinci', 'Riau', 'AKTIF', '1', '1', '1'),
('320', 'Pandeglang', FALSE, 'Panimbang', 'Banten', 'AKTIF', '1', '1', '1'),
('321', 'Ciamis', TRUE, 'Panjalu', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('322', 'Situbondo', FALSE, 'Panji', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('323', 'Kediri', TRUE, 'Pare', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('324', 'Pariaman', FALSE, 'Pariaman Tengah', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('325', 'Parigi Mountong', FALSE, 'Parigi', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('326', 'Parigi Mountong', FALSE, 'Parigi Utara', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('327', 'Ngawi', FALSE, 'Paron', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('328', 'Pasaman Barat', FALSE, 'Pasaman', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('329', 'Mamuju', FALSE, 'Pasangkayu', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('330', 'Baubau', FALSE, 'Pasarwajo', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('331', 'Purwakarta', TRUE, 'Pasawahan', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('332', 'Bandung', FALSE, 'Pasir Jambu', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('333', 'Subang', FALSE, 'Pasirkareumbi', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('334', 'Jember', FALSE, 'Patrang', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('335', 'Indramayu', FALSE, 'Patrol', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('336', 'Takalar', FALSE, 'Pattallassang', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('337', 'Padang', FALSE, 'Pauh', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('338', 'Payakumbuh', FALSE, 'Payakumbuh Barat', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('339', 'Jepara', FALSE, 'Pecangaan', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('340', 'Deli Serdang', FALSE, 'Pecut Sei Tuan', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('341', 'Karawang', FALSE, 'Pedes', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('342', 'Lampung Timur', FALSE, 'Pekalongan', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('343', 'Pemalang', FALSE, 'Pemalang Kota', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('344', 'Lampung Selatan', FALSE, 'Penengahan', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('345', 'Serdang Bedagai', FALSE, 'Perbaungan', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('346', 'Kebumen', FALSE, 'Petanahan', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('347', 'Pemalang', FALSE, 'Petarukan', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('348', 'Magetan', FALSE, 'Plaosan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('349', 'Kediri', TRUE, 'Plemahan', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('350', 'Jombang', FALSE, 'Ploso', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('351', 'Tuban', FALSE, 'Plumpang', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('352', 'Pasuruan', FALSE, 'Pohjentrek', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('353', 'Area WO', FALSE, 'Point WO', '', 'AKTIF', '1', '1', '1'),
('354', 'Klaten', TRUE, 'Polanharjo', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('355', 'Polman', FALSE, 'Polewali', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('356', 'Kolaka', FALSE, 'Pomalaa', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('357', 'Konawe', FALSE, 'Pondidaha', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('358', 'Poso', FALSE, 'Poso Kota', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('359', 'Poso', FALSE, 'Poso Kota Selatan', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('360', 'Poso', FALSE, 'Poso Pesisir Utara', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('361', 'Ogan Ilir', FALSE, 'Prabumulih', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('362', 'Wonogiri', FALSE, 'Pracimantoro', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('363', 'Ponorogo', FALSE, 'Pule', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('364', 'Ponorogo', FALSE, 'Pulung', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('365', 'Banjarnegara', FALSE, 'Punggelan', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('366', 'Mojokerto', FALSE, 'Puri', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('367', 'Purworejo', TRUE, 'Purworejo', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('368', 'Pasuruan', FALSE, 'Purwosari', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('369', 'Ciamis', TRUE, 'Rajadesa', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('370', 'Pemalang', FALSE, 'Randu Dongkal', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('371', 'Blora', FALSE, 'Randublatung', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('372', 'Lebak', FALSE, 'Rangkasbitung', 'Banten', 'AKTIF', '1', '1', '1'),
('373', 'Kendari', FALSE, 'Ranomeeto', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('374', 'Makassar', FALSE, 'Rappocini', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('375', 'Nganjuk', FALSE, 'Rejoso', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('376', 'Banyuwangi', FALSE, 'Rogojampi', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('377', 'Pekanbaru', FALSE, 'Rumbai', 'Riau', 'AKTIF', '1', '1', '1'),
('378', 'Ponorogo', FALSE, 'Sambit', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('379', 'Blitar', TRUE, 'Sananwetan', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('380', 'Sarolangun', FALSE, 'Sarolangun', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('381', 'Palembang', FALSE, 'Seberang Ulu I', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('382', 'Palembang', FALSE, 'Seberang Ulu II', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('383', 'Gresik', FALSE, 'Sedayu', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('384', 'Serdang Bedagai', FALSE, 'Sei Bamban', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('385', 'Serdang Bedagai', FALSE, 'Sei Rampah', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('386', 'Banyuasin', FALSE, 'Sekayu', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('387', 'Yogyakarta', FALSE, 'Seyegan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('388', 'Simalungun', FALSE, 'Siantar', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('389', 'Simalungun', FALSE, 'Siantar Martoba', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('390', 'Pariaman', FALSE, 'Sicincin', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('391', 'Cilacap', FALSE, 'Sidareja', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('392', 'Lampung Selatan', FALSE, 'Sidomulyo', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('393', 'Surabaya', FALSE, 'Simokerto', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('394', 'Asahan', FALSE, 'Simpang Empat', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('395', 'Donggala', FALSE, 'Sindue', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('396', 'Sarolangun', FALSE, 'Singkut', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('397', 'Malang', FALSE, 'Singosari', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('398', 'Bone', FALSE, 'Sinjai Utara', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('399', 'Banyumas', TRUE, 'Sokaraja', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('400', 'Tuban', FALSE, 'Soko', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('401', 'Pangkajene', FALSE, 'Soppeng Riaja', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('402', 'Parepare', FALSE, 'Soreang', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('403', 'Kebumen', FALSE, 'Sruweng', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('404', 'Langkat', FALSE, 'Stabat', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('405', 'Sukabumi', TRUE, 'Sukaraja', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('406', 'Banyuasin', FALSE, 'Sukarami', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('407', 'Purwakarta', FALSE, 'Sukatani', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('408', 'Karawang', FALSE, 'Sukatani 2', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('409', 'Blitar', FALSE, 'Sukorejo', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('410', 'Blora', FALSE, 'Sulang', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('411', 'Banyumas', FALSE, 'Sumbang', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('412', 'Cirebon', FALSE, 'Sumber', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('413', 'Probolinggo', FALSE, 'Sumberasih', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('414', 'Sumedang', FALSE, 'Sumedang Utara', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('415', 'Banyuasin', FALSE, 'Sungai Lilin', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('416', 'Pesisir Selatan', FALSE, 'Sutera', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('417', 'Jepara', FALSE, 'Tahunan', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('418', 'Majalengka', TRUE, 'Talaga', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('419', 'Banyuasin', FALSE, 'Talang Kelapa', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('420', 'Tanggamus', FALSE, 'Talang Padang', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('421', 'Makassar', FALSE, 'Tallo', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('422', 'Blitar', FALSE, 'Talun', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('423', 'Makassar', FALSE, 'Tamalate', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('424', 'Jeneponto', FALSE, 'Tamalatea', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('425', 'Madiun', FALSE, 'Taman', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('426', 'Sidoarjo', FALSE, 'Taman 1', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('427', 'Bondowoso', FALSE, 'Tamanan', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('428', 'Pekanbaru', FALSE, 'Tambang', 'Riau', 'AKTIF', '1', '1', '1'),
('429', 'Rokan Hulu', FALSE, 'Tambusai', 'Riau', 'AKTIF', '1', '1', '1'),
('430', 'Bone', FALSE, 'Tanete Riattang', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('431', 'Pangkajene', FALSE, 'Tanete Rilau', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('432', 'Jember', TRUE, 'Tanggul', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('433', 'Sidoarjo', FALSE, 'Tanggulangin', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('434', 'Lampung Selatan', FALSE, 'Tanjung Bintang', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('435', 'Deli Serdang', FALSE, 'Tanjung Morawa', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('436', 'Langkat', FALSE, 'Tanjung Pura', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('437', 'Ogan Ilir', FALSE, 'Tanjung Raja', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('438', 'Bandar Lampung', FALSE, 'Tanjung Senang', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('439', 'Subang', FALSE, 'Tanjung Siang', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('440', 'Batu Bara', FALSE, 'Tanjung Tiram', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('441', 'Sumedang', TRUE, 'Tanjungsari', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('442', 'Mamuju', FALSE, 'Tapalang', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('443', 'Bondowoso', FALSE, 'Tapen', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('444', 'Simalungun', FALSE, 'Tapian Dolok', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('445', 'Kediri', FALSE, 'Tarokan', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('446', 'Tegal', FALSE, 'Tarub', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('447', 'Donggala', FALSE, 'Tawaeli', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('448', 'Kediri', FALSE, 'Tawang', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('449', 'Kota Jambi', FALSE, 'Telanaipura', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('450', 'Bandar Lampung', FALSE, 'Teluk Betung Barat', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('451', 'Tangerang', TRUE, 'Teluknaga', 'Banten', 'AKTIF', '1', '1', '1'),
('452', 'Semarang', FALSE, 'Tembalang', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('453', 'Bone', FALSE, 'Tempe', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('454', 'Siak', FALSE, 'Tenayan Raya', 'Riau', 'AKTIF', '1', '1', '1'),
('455', 'Semarang', TRUE, 'Tengaran', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('456', 'Bogor', TRUE, 'Tenjo', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('457', 'Lampung Tengah', FALSE, 'Terbanggi Besar', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('458', 'Indramayu', FALSE, 'Terisi', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('459', 'Lampung Tengah', FALSE, 'Terusan Nyunyai', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('460', 'Konawe', FALSE, 'Tongauna', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('461', 'Mamuju', FALSE, 'Topoyo', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('462', 'Grobogan', FALSE, 'Toroh', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('463', 'Parigi Mountong', FALSE, 'Torue', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('464', 'Mojokerto', FALSE, 'Trawas', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('465', 'Ponorogo', FALSE, 'Trenggalek Kota', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('466', 'Lampung Tengah', FALSE, 'Trimurejo', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('467', 'Klaten', FALSE, 'Trucuk', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('468', 'Siak', FALSE, 'Tualang', 'Riau', 'AKTIF', '1', '1', '1'),
('469', 'Tuban', FALSE, 'Tuban', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('470', 'Lubuklinggau', FALSE, 'Tugumulyo', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('471', 'Tulang Bawang', FALSE, 'Tulang Bawang Tengah', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('472', 'Sidoarjo', FALSE, 'Tulangan', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('473', 'Malang', FALSE, 'Tumpang', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('474', 'Semarang', TRUE, 'Tuntang', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('475', 'Malang', FALSE, 'Turen', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('476', 'Jeneponto', FALSE, 'Ujung Bulu', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('477', 'Palopo', FALSE, 'Wara', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('478', 'Sukabumi', FALSE, 'Warung Kiara', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('479', 'Parepare', FALSE, 'Watang Sawitto', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('480', 'Yogyakarta', TRUE, 'Wates', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('481', 'Konawe', FALSE, 'Wawotobi', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('482', 'Lampung Timur', FALSE, 'Way Jepara', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('483', 'Klaten', FALSE, 'Wedi', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('484', 'Ngawi', FALSE, 'Widodaren', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('485', 'Pekalongan', TRUE, 'Wiradesa', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('486', 'Grobogan', FALSE, 'Wirosari', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('487', 'Surabaya', FALSE, 'Wiyung', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('488', 'Blitar', FALSE, 'Wlingi', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('489', 'Surabaya', FALSE, 'Wonokromo', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('490', 'Polman', FALSE, 'Wonomulyo', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('491', 'Wonogiri', FALSE, 'Wonosari', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('492', 'Pesisir Selatan', FALSE, 'XI Koto Tarusan', 'Sumatera Barat', 'AKTIF', '1', '1', '1');

INSERT INTO "rencana_pemeriksaan" ("id_rencana", "link_majelis", "nama_rencana", "pembuat", "status") 
VALUES 
('1', 'test', 'alpha', '1', '1'),
('2', 'test', 'beta', '1', '1'),
('3', 'test', 'gamma', '1', '1'),
('4', 'test', 'delta', '1', '1');

INSERT INTO "tugas_pemeriksaan" ("id_tugas", "tanggal_mulai", "tanggal_selesai", "kantor_cabang", "pelaksana", "rencana_pemeriksaan") VALUES 
('1', '2020-03-24', '2020-04-01', '1', '1', '1'),
('2', '2020-04-02', '2020-04-09', '2', '1', '1'),
('3', '2020-04-10', '2020-04-17', '3', '5', '1'),
('4', '2020-05-01', '2020-05-08', '4', '5', '2'),
('5', '2020-05-09', '2020-05-16', '5', '5', '3'),
('6', '2020-05-17', '2020-05-24', '3', '5', '4');

SELECT setval('employee_id_employee_seq', max(id_employee)) FROM employee;
SELECT setval('kantor_cabang_id_kantor_seq', max(id_kantor)) FROM kantor_cabang;
SELECT setval('reminder_template_id_reminder_template_seq', max(id_reminder_template)) FROM reminder_template;
SELECT setval('rencana_pemeriksaan_id_rencana_seq', max(id_rencana)) FROM rencana_pemeriksaan;
SELECT setval('risiko_id_risiko_seq', max(id_risiko)) FROM risiko;
SELECT setval('risk_level_id_level_seq', max(id_level)) FROM risk_level;
SELECT setval('risk_rating_id_rating_seq', max(id_rating)) FROM risk_rating;
SELECT setval('sop_id_sop_seq', max(id_sop)) FROM sop;
SELECT setval('tugas_pemeriksaan_id_tugas_seq', max(id_tugas)) FROM tugas_pemeriksaan;
