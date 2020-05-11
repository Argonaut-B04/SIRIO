INSERT IGNORE INTO `role` (`id_role`, `nama_role`) VALUES
(1, 'Administrator'),
(2, 'Supervisor'),
(3, 'Manajer Operational Risk'),
(4, 'QA Lead Operational Risk'),
(5, 'QA Officer Operational Risk'),
(6, 'Branch Manager'),
(7, 'Super QA Officer Operational Risk');

INSERT IGNORE INTO `access_permissions` (`akses_bukti_pelaksanaan`, `akses_hapus_risiko`, `akses_persetujuan_bukti_pelaksanaan`, `akses_rekomendasi`, `akses_risiko`, `akses_risk_level`, `akses_risk_rating`, `akses_tabel_rekomendasi`, `akses_tabel_risiko`, `akses_tambah_bukti_pelaksanaan`, `akses_tambah_risiko`, `akses_ubah_bukti_pelaksanaan`, `akses_ubah_hierarki`, `akses_ubah_risiko`, `atur_tenggat_waktu`, `ubah_reminder`, `ubah_risk_level`, `ubah_risk_rating`, `role_id_role`)
VALUES
(b'0', b'0', b'0', b'0', b'0', b'1', b'1', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', b'0', '1'),
(b'1', b'0', b'1', b'0', b'1', b'1', b'1', b'0', b'1', b'1', b'0', b'0', b'1', b'0', b'0', b'0', b'1', b'1', '2'),
(b'1', b'0', b'1', b'0', b'1', b'1', b'1', b'0', b'1', b'0', b'0', b'0', b'1', b'0', b'0', b'0', b'1', b'1', '3'),
(b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', '4'),
(b'1', b'1', b'1', b'0', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'0', b'1', b'1', b'1', b'0', b'0', '5'),
(b'1', b'0', b'0', b'0', b'0', b'0', b'0', b'1', b'0', b'1', b'0', b'1', b'0', b'0', b'0', b'0', b'0', b'0', '6'),
(b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', b'1', '7');

INSERT IGNORE INTO `reminder_template` (`id_reminder_template`, `body`, `global`, `subjects`) 
VALUES ('1', 'The global template for your email', b'1', 'A Reminder for Recommendation Assignment');

INSERT IGNORE INTO `status_bukti_pelaksanaan` (`id_status_bukti`, `keterangan_status`, `nama_status`) 
VALUES 
('1', NULL, 'Menunggu Persetujuan'), 
('2', NULL, 'Disetujui'), 
('3', NULL, 'Ditolak');

INSERT IGNORE INTO `status_rencana_pemeriksaan` (`id_status_rencana`, `keterangan_status`, `nama_status`)
VALUES 
('1', NULL, 'Draft'), 
('2', NULL, 'Sedang Dijalankan'), 
('3', NULL, 'Selesai');

INSERT IGNORE INTO `status_hasil_pemeriksaan` (`id_status_hasil`, `keterangan_status`, `nama_status`) 
VALUES 
('1', NULL, 'Menunggu Persetujuan'), 
('2', NULL, 'Draft'), 
('3', NULL, 'Ditolak'), 
('4', NULL, 'Menunggu Pelaksanaan'), 
('5', NULL, 'Selesai');

INSERT IGNORE INTO `status_rekomendasi` (`id_status_rekomendasi`, `dapat_set_tenggat_waktu`, `keterangan_status`, `nama_status`) 
VALUES 
('1', b'0', NULL, 'Draft'), 
('2', b'0', NULL, 'Menunggu Persetujuan'), 
('3', b'0', NULL, 'Ditolak'), 
('4', b'1', NULL, 'Menunggu Pengaturan Tenggat Waktu'), 
('5', b'1', NULL, 'Menunggu Pelaksanaan'), 
('6', b'0', NULL, 'Sedang Dilaksanakan'), 
('7', b'0', NULL, 'Selesai');


INSERT IGNORE INTO `employee` (`id_employee`, `email`, `jabatan`, `nama`, `no_hp`, `password`, `status`, `username`, `reminder_template`, `role`) 
VALUES 
('1', 'administrator@sirio.com', 'Administrator', 'Aku Administrator', '0123', '', 'AKTIF', 'administrator', '1', '1'),
('2', 'supervisor@sirio.com', 'Supervisor', 'Aku Supervisor', '0123', '', 'AKTIF', 'supervisor', '1', '2'),
('3', 'manajer@sirio.com', 'QA Manajer', 'Aku Manajer', '0123', '', 'AKTIF', 'manajer', '1', '3'),
('4', 'lead@sirio.com', 'QA Lead', 'Aku Lead', '0123', '', 'AKTIF', 'leadlead', '1', '4'),
('5', 'officer@sirio.com', 'QA Officer', 'Aku Officer', '0123', '', 'AKTIF', 'qaofficer', '1', '5'),
('6', 'bm@sirio.com', 'Branch Manager', 'Aku BM', '0123', '', 'AKTIF', 'branchmanager', '1', '6'),
('7', 'superQA@sirio.com', 'Super QA', 'Aku SuperQA', '0123', '', 'AKTIF', 'superofficer', '1', '7');


INSERT IGNORE INTO `sop` (`id_sop`, `judul`, `kategori`, `link_dokumen`, `status`, `pembuat`) VALUES ('1', 'test', 'test', 'https://test', 'AKTIF', '1');

INSERT IGNORE INTO `risiko` (`id_risiko`, `deskripsi`, `detail_uraian`, `ketentuan_sampel`, `metodologi`, `nama_risiko`, `risiko_kategori`, `status`, `parent`, `sop`) 
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

INSERT IGNORE INTO `risk_level` (`id_level`, `bobot_level`, `keterangan_level`, `nama_level`, `status`, `pengelola`) 
VALUES 
('1', '-4', 'High Risk', 'High Risk', 'AKTIF', '1'),
('2', '-2', 'Medium Risk', 'Medium Risk', 'AKTIF', '1'),
('3', '-1', 'Low Risk', 'Low Risk', 'AKTIF', '1'),
('4', '0', 'No Risk', 'No Risk', 'AKTIF', '1');

INSERT IGNORE INTO `risk_rating` (`id_rating`, `keterangan_rating`, `nama_rating`, `skor_maksimal`, `skor_minimal`, `pengelola`) VALUES ('1', 'Bagus', 'Bagus', '50', '25', '1');

INSERT IGNORE INTO `kantor_cabang` (`id_kantor`, `area`, `kunjungan_audit`, `nama_kantor`, `regional`, `status`, `pembuat`, `pemilik`, `risk_rating`) VALUES 
('1', 'Lampung Utara', b'0', 'Abung Selatan', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('2', 'Cilacap', b'0', 'Adipala', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('3', 'Tegal', b'0', 'Adiwerna', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('4', 'Asahan', b'0', 'Air Joman', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('5', 'Batu Bara', b'0', 'Air Putih', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('6', 'Banyumas', b'0', 'Ajibarang', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('7', 'Semarang', b'0', 'Ambarawa', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('8', 'Agam', b'0', 'Ampe Angke', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('9', 'Cirebon', b'0', 'Arjawinangun', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('10', 'Situbondo', b'0', 'Asembagus', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('11', 'Cirebon', b'1', 'Astanajapura', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('12', 'Cirebon', b'0', 'Babakan', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('13', 'Gowa', b'0', 'Bajeng Barat', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('14', 'Donggala', b'0', 'Balaesang', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('15', 'Yogyakarta', b'0', 'Bambanglipuro', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('16', 'Palu', b'0', 'Banawa', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('17', 'Tuban', b'0', 'Bancar', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('18', 'Pekalongan', b'0', 'Bandar', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('19', 'Simalungun', b'0', 'Bandar Simalungun', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('20', 'Lampung Timur', b'0', 'Bandar Sribawono', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('21', 'Magelang', b'0', 'Bandongan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('22', 'Tulungagung', b'0', 'Bandung', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('23', 'Polman', b'0', 'Banggae', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('24', 'Pasuruan', b'0', 'Bangil', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('25', 'Pekanbaru', b'0', 'Bangkinang Kota', 'Riau', 'AKTIF', '1', '1', '1'),
('26', 'Banyuwangi', b'0', 'Bangorejo', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('27', 'Mojokerto', b'0', 'Bangsal', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('28', 'Yogyakarta', b'1', 'Banguntapan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('29', 'Tulang Bawang', b'0', 'Banjar Agung', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('30', 'Tulang Bawang', b'0', 'Banjar Margo', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('31', 'Brebes', b'0', 'Banjarharjo', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('32', 'Solo', b'0', 'Banjarsari', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('33', 'Ciamis', b'0', 'Banjarsari 2', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('34', 'Jeneponto', b'0', 'Bantaeng', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('35', 'Agam', b'0', 'Banuhampu', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('36', 'Palembang', b'0', 'Banyuasin I', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('37', 'Banyuasin', b'0', 'Banyuasin III', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('38', 'Banyumas', b'0', 'Banyumas', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('39', 'Tolitoli', b'0', 'Baolan', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('40', 'Gowa', b'0', 'Barombong', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('41', 'Serang', b'0', 'Baros', 'Banten', 'AKTIF', '1', '1', '1'),
('42', 'Palopo', b'0', 'Barra', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('43', 'Agam', b'0', 'Baso', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('44', 'Lampung Timur', b'0', 'Batanghari', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('45', 'Baubau', b'0', 'Batupoaro', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('46', 'Ogan Komering Ulu', b'0', 'Baturaja Timur', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('47', 'Wonogiri', b'0', 'Baturetno', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('48', 'Pemalang', b'0', 'Belik', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('49', 'Ogan Komering Ulu', b'0', 'Belitang', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('50', 'Gresik', b'0', 'Benjeng', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('51', 'Nganjuk', b'1', 'Berbek', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('52', 'Deli Serdang', b'0', 'Beringin', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('53', 'Bondowoso', b'0', 'Besuki', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('54', 'Jeneponto', b'0', 'Binamu', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('55', 'Kota Medan', b'0', 'Binjai Timur', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('56', 'Palu', b'0', 'Biromaru', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('57', 'Malang', b'0', 'Blimbing', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('58', 'Purwakarta', b'0', 'Bojong', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('59', 'Tegal', b'0', 'Bojong 2', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('60', 'Cianjur', b'0', 'Bojongpicung', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('61', 'Bondowoso', b'0', 'Bondowoso', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('62', 'Palopo', b'0', 'Bonebone', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('63', 'Gowa', b'0', 'Bontomarannu', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('64', 'Gowa', b'0', 'Bontonompo', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('65', 'Bojonegoro', b'0', 'Boureno', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('66', 'Tulungagung', b'0', 'Boyolangu', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('67', 'Sumedang', b'0', 'Buahdua', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('68', 'Partner', b'0', 'Bukalapak', 'Partner', 'AKTIF', '1', '1', '1'),
('69', 'Lampung Utara', b'0', 'Bukit Kemuning', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('70', 'Brebes', b'1', 'Bulakamba', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('71', 'Brebes', b'1', 'Bumiayu', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('72', 'Tegal', b'0', 'Bumijawa', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('73', 'Subang', b'1', 'Bunihayu', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('74', 'Karawang', b'0', 'Cabangbungin', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('75', 'Cianjur', b'0', 'Campaka', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('76', 'Polman', b'0', 'Campalagian', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('77', 'Bandung', b'1', 'Cangkuang', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('78', 'Klaten', b'0', 'Cawas', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('79', 'Gresik', b'0', 'Cerme', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('80', 'Kuningan', b'0', 'Ciawigebang', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('81', 'Sukabumi', b'1', 'Cibadak', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('82', 'Lebak', b'0', 'Cibeber', 'Banten', 'AKTIF', '1', '1', '1'),
('83', 'Sukabumi', b'0', 'Cicurug', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('84', 'Majalengka', b'1', 'Cigasong', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('85', 'Ciamis', b'0', 'Cijeungjing', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('86', 'Cianjur', b'0', 'Cikalong Kulon', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('87', 'Serang', b'0', 'Cikande', 'Banten', 'AKTIF', '1', '1', '1'),
('88', 'Lebak', b'0', 'Cikulur', 'Banten', 'AKTIF', '1', '1', '1'),
('89', 'Cilacap', b'0', 'Cilacap Selatan', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('90', 'Cianjur', b'0', 'Cilaku', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('91', 'Karawang', b'0', 'Cilamaya Wetan', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('92', 'Kuningan', b'0', 'Cilimus', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('93', 'Pandeglang', b'0', 'Cimanuk', 'Banten', 'AKTIF', '1', '1', '1'),
('94', 'Lebak', b'0', 'Cipanas', 'Banten', 'AKTIF', '1', '1', '1'),
('95', 'Bandung', b'0', 'Ciparay', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('96', 'Serang', b'1', 'Ciruas', 'Banten', 'AKTIF', '1', '1', '1'),
('97', 'Sukabumi', b'0', 'Cisaat', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('98', 'Bogor', b'0', 'Ciseeng', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('99', 'Sumedang', b'0', 'Cisitu', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('100', 'Tangerang', b'0', 'Cisoka', 'Banten', 'AKTIF', '1', '1', '1'),
('101', 'Serang', b'0', 'Ciwandan', 'Banten', 'AKTIF', '1', '1', '1'),
('102', 'Banyuwangi', b'0', 'Cluring', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('103', 'Sumedang', b'0', 'Conggeang', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('104', 'Tolitoli', b'0', 'Dampal Selatan', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('105', 'Asahan', b'0', 'Datuk Bandar Timur', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('106', 'Klaten', b'0', 'Delanggu', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('107', 'Jombang', b'0', 'Diwek', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('108', 'Madiun', b'0', 'Dolopo', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('109', 'Tolitoli', b'0', 'Dondo', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('110', 'Probolinggo', b'0', 'Dringgu', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('111', 'Dumai', b'0', 'Dumai Selatan', '', 'AKTIF', '1', '1', '1'),
('112', 'Blora', b'0', 'Gabus', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('113', 'Pesawaran', b'0', 'Gading Rejo', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('114', 'Tolitoli', b'0', 'Galang', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('115', 'Takalar', b'0', 'Galesong', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('116', 'Takalar', b'0', 'Galesong Selatan', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('117', 'Tulungagung', b'0', 'Gandusari', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('118', 'Blitar', b'0', 'Garum', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('119', 'Sidoarjo', b'0', 'Gedangan', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('120', 'Pesawaran', b'0', 'Gedong Tataan', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('121', 'Ogan Ilir', b'0', 'Gelumbang', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('122', 'Solo', b'0', 'Gemolong', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('123', 'Banyuwangi', b'0', 'Genteng', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('124', 'Tanggamus', b'0', 'Gisting', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('125', 'Kebumen', b'0', 'Gombong', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('126', 'Solo', b'0', 'Gondang', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('127', 'Nganjuk', b'0', 'Gondang 2', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('128', 'Magelang', b'0', 'Grabag', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('129', 'Grobogan', b'0', 'Gubug', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('130', 'Jombang', b'0', 'Gudo', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('131', 'Payakumbuh', b'0', 'Guguak', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('132', 'Solok', b'0', 'Gunung Talang', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('133', 'Payakumbuh', b'0', 'Harau', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('134', 'Subang', b'0', 'Haurgeulis', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('135', 'Palembang', b'0', 'Ilir Barat I', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('136', 'Ogan Ilir', b'0', 'Indralaya', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('137', 'Indramayu', b'0', 'Indramayu', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('138', 'Kota Jambi', b'0', 'Jambi Timur', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('139', 'Bogor', b'0', 'Jasinga', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('140', 'Indramayu', b'0', 'Jatibarang', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('141', 'Brebes', b'0', 'Jatibarang 2', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('142', 'Klaten', b'0', 'Jatinom', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('143', 'Wonogiri', b'0', 'Jatisrono', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('144', 'Ponorogo', b'0', 'Jenangan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('145', 'Jember', b'1', 'Jenggawah', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('146', 'Blora', b'0', 'Jepon', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('147', 'Cilacap', b'1', 'Jeruklegi', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('148', 'Madiun', b'0', 'Jiwan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('149', 'Klaten', b'0', 'Jogonalan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('150', 'Jombang', b'0', 'Jombang', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('151', 'Pesisir Selatan', b'0', 'Jurai', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('152', 'Banyuwangi', b'0', 'Kabat', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('153', 'Kendari', b'0', 'Kadia', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('154', 'Pekalongan', b'0', 'Kajen', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('155', 'Lampung Selatan', b'0', 'Kalianda', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('156', 'Palembang', b'0', 'Kalidoni', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('157', 'Banjarnegara', b'0', 'Kaligonda', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('158', 'Banyuwangi', b'0', 'Kalipuro', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('159', 'Lampung Tengah', b'0', 'Kalirejo', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('160', 'Jember', b'0', 'Kalisat', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('161', 'Bojonegoro', b'0', 'Kalitidu', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('162', 'Indramayu', b'0', 'Kandang Haur', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('163', 'Blitar', b'0', 'Kanigoro', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('164', 'Bojonegoro', b'0', 'Kapas', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('165', 'Cianjur', b'0', 'Karang Tengah', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('166', 'Solo', b'0', 'Karanganyar', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('167', 'Pekalongan', b'0', 'Karanganyar 2', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('168', 'Grobogan', b'0', 'Karangawen', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('169', 'Lamongan', b'0', 'Karanggeneng', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('170', 'Ngawi', b'0', 'Karangjati', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('171', 'Banyumas', b'0', 'Karanglewas', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('172', 'Parigi Mountong', b'0', 'Kasimbar', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('173', 'Ogan Komering Ilir', b'0', 'Kayu Agung', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('174', 'Kebumen', b'0', 'Kebumen', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('175', 'Bandar Lampung', b'0', 'Kedaton', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('176', 'Pesawaran', b'0', 'Kedondong', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('177', 'Lamongan', b'0', 'Kedungpring', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('178', 'Tulungagung', b'0', 'Kedungwaru', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('179', 'Pekalongan', b'1', 'Kedungwuni', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('180', 'Banjarnegara', b'0', 'Kejobong', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('181', 'Bogor', b'0', 'Kemang', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('182', 'Bandar Lampung', b'0', 'Kemiling', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('183', 'Purworejo', b'0', 'Kemiri', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('184', 'Mojokerto', b'0', 'Kemlagi', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('185', 'Jember', b'1', 'Kencong', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('186', 'Kendari', b'0', 'Kendari Barat', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('187', 'Malang', b'1', 'Kepanjen', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('188', 'Nganjuk', b'1', 'Kertosono', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('189', 'Pasaman Barat', b'0', 'Kinali', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('190', 'Asahan', b'0', 'Kisaran Barat', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('191', 'Karawang', b'1', 'Klari', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('192', 'Klaten', b'0', 'Klaten Selatan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('193', 'Klaten', b'0', 'Klaten Utara', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('194', 'Baubau', b'0', 'Kokalukuna', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('195', 'Kolaka', b'0', 'Kolaka', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('196', 'Tanggamus', b'0', 'Kota Agung', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('197', 'Kota Jambi', b'0', 'Kota Baru', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('198', 'Lampung Tengah', b'0', 'Kota Gajah', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('199', 'Lampung Timur', b'0', 'Kota Metro', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('200', 'Lampung Utara', b'0', 'Kotabumi', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('201', 'Padang', b'0', 'Koto Tangah', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('202', 'Grobogan', b'0', 'Kradenan', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('203', 'Serang', b'1', 'Kramatwatu', 'Banten', 'AKTIF', '1', '1', '1'),
('204', 'Mojokerto', b'0', 'Kranggan', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('205', 'Sidoarjo', b'1', 'Krian', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('206', 'Tangerang', b'0', 'Kronjo', 'Banten', 'AKTIF', '1', '1', '1'),
('207', 'Kuningan', b'0', 'Kuningan', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('208', 'Padang', b'0', 'Kuranji', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('209', 'Banjarnegara', b'0', 'Kutasari', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('210', 'Purworejo', b'1', 'Kutoarjo', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('211', 'Pangkajene', b'0', 'Labakkang', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('212', 'Pandeglang', b'0', 'Labuan', 'Banten', 'AKTIF', '1', '1', '1'),
('213', 'Donggala', b'0', 'Labuan 2', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('214', 'Kendari', b'0', 'Laeya', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('215', 'Poso', b'0', 'Lage', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('216', 'Lahat', b'0', 'Lahat', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('217', 'Bone', b'0', 'Lalabata', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('218', 'Palopo', b'0', 'Lamasi', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('219', 'Lamongan', b'0', 'Lamongan', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('220', 'Ciamis', b'0', 'Langensari', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('221', 'Payakumbuh', b'0', 'Lareh', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('222', 'Kolaka', b'0', 'Lasusua', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('223', 'Tegal', b'0', 'Lebaksiu', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('224', 'Tangerang', b'1', 'Legok', 'Banten', 'AKTIF', '1', '1', '1'),
('225', 'Majalengka', b'0', 'Lemahsugih', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('226', 'Solok', b'0', 'Lembah Gumanti', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('227', 'Pasaman Barat', b'0', 'Lembah Melintang', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('228', 'Ogan Komering Ilir', b'0', 'Lempuing', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('229', 'Bogor', b'1', 'Leuwiliang', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('230', 'Majalengka', b'0', 'Leuwimunding', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('231', 'Majalengka', b'0', 'Ligung', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('232', 'Solok', b'0', 'Lima Kaum', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('233', 'Batu Bara', b'0', 'Lima Puluh', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('234', 'Purworejo', b'0', 'Loano', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('235', 'Nganjuk', b'0', 'Loceret', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('236', 'Konawe', b'0', 'Loea', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('237', 'Pariaman', b'0', 'Lubuk Alung', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('238', 'Agam', b'0', 'Lubuk Basung', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('239', 'Padang', b'0', 'Lubuk Begalung', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('240', 'Ogan Komering Ulu', b'0', 'Lubuk Raja', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('241', 'Solok', b'0', 'Lubuk Sikarah', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('242', 'Lubuklinggau', b'0', 'Lubuklinggau Barat I', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('243', 'Lubuklinggau', b'0', 'Lubuklinggau Timur I', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('244', 'Kuningan', b'0', 'Luragung', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('245', 'Madiun', b'0', 'Madiun', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('246', 'Pandeglang', b'0', 'Majasari', 'Banten', 'AKTIF', '1', '1', '1'),
('247', 'Cilacap', b'1', 'Majenang', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('248', 'Kuningan', b'0', 'Maleber', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('249', 'Lebak', b'0', 'Malingping', 'Banten', 'AKTIF', '1', '1', '1'),
('250', 'Mamuju', b'0', 'Mamuju', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('251', 'Banjarnegara', b'0', 'Mandiraja', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('252', 'Takalar', b'0', 'Mangarabombang', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('253', 'Makassar', b'0', 'Manggala', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('254', 'Palu', b'0', 'Mantikulore', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('255', 'Lamongan', b'0', 'Mantup', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('256', 'Gresik', b'0', 'Manyar', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('257', 'Magetan', b'0', 'Maospati', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('258', 'Tegal', b'0', 'Margasari', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('259', 'Makassar', b'0', 'Mariso', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('260', 'Probolinggo', b'0', 'Maron', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('261', 'Pekanbaru', b'0', 'Marpoyan Damai', 'Riau', 'AKTIF', '1', '1', '1'),
('262', 'Ogan Komering Ulu', b'0', 'Martapura', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('263', 'Palopo', b'0', 'Masamba', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('264', 'Solo', b'0', 'Masaran', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('265', 'Tangerang', b'0', 'Mauk', 'Banten', 'AKTIF', '1', '1', '1'),
('266', 'Jepara', b'0', 'Mayong', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('267', 'Kota Medan', b'0', 'Medan Marelan', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('268', 'Kota Medan', b'0', 'Medan Sunggal', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('269', 'Kota Medan', b'0', 'Medan Timur', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('270', 'Lubuklinggau', b'0', 'Megang Sakti', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('271', 'Madiun', b'1', 'Mejayan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('272', 'Pandeglang', b'0', 'Menes', 'Banten', 'AKTIF', '1', '1', '1'),
('273', 'Parigi Mountong', b'0', 'Mepanga', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('274', 'Tuban', b'0', 'Merakurak', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('275', 'Situbondo', b'0', 'Mlandingan', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('276', 'Jepara', b'0', 'Mlonggo', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('277', 'Mojokerto', b'1', 'Mojo Pacet', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('278', 'Jombang', b'1', 'Mojoagung', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('279', 'Kota Jambi', b'0', 'Muara Bulian', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('280', 'Lahat', b'0', 'Muara Enim', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('281', 'Magelang', b'0', 'Mungkid', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('282', 'Sarolangun', b'0', 'Nalo Tantan', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('283', 'Lampung Selatan', b'0', 'Natar', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('284', 'Wonogiri', b'0', 'Ngadirojo', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('285', 'Yogyakarta', b'0', 'Ngaglik', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('286', 'Semarang', b'0', 'Ngaliyan', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('287', 'Kolaka', b'0', 'Ngapa', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('288', 'Magetan', b'0', 'Ngariboyo', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('289', 'Kediri', b'1', 'Ngasem', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('290', 'Blora', b'0', 'Ngawen', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('291', 'Ngawi', b'0', 'Ngawi', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('292', 'Lamongan', b'0', 'Ngimbang', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('293', 'Ngawi', b'0', 'Ngrambe', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('294', 'Nganjuk', b'0', 'Ngronggot', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('295', 'Tulungagung', b'0', 'Ngunut', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('296', 'Wonogiri', b'0', 'Nguter', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('297', 'Bandung', b'0', 'Pacet', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('298', 'Ogan Komering Ilir', b'0', 'Padamaran', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('299', 'Serdang Bedagai', b'0', 'Padang Hulu', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('300', 'Pariaman', b'0', 'Padang Panjang Barat', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('301', 'Bojonegoro', b'0', 'Padangan', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('302', 'Subang', b'0', 'Pagaden', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('303', 'Lahat', b'0', 'Pagaralam', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('304', 'Tanggamus', b'0', 'Pagelaran', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('305', 'Probolinggo', b'0', 'Paiton', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('306', 'Magelang', b'0', 'Pakis', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('307', 'Parepare', b'0', 'Paleteang', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('308', 'Cirebon', b'0', 'Palimanan', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('309', 'Gowa', b'0', 'Pallangga', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('310', 'Palu', b'0', 'Palu Selatan', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('311', 'Sarolangun', b'0', 'Pamenang', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('312', 'Poso', b'0', 'Pamona Puselemba', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('313', 'Situbondo', b'0', 'Panarukan', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('314', 'Deli Serdang', b'0', 'Pancur Batu', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('315', 'Pasuruan', b'0', 'Pandaan', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('316', 'Magetan', b'0', 'Panekan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('317', 'Bandung', b'0', 'Pangalengan', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('318', 'Pangkajene', b'0', 'Pangkajene', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('319', 'Siak', b'0', 'Pangkalan Kerinci', 'Riau', 'AKTIF', '1', '1', '1'),
('320', 'Pandeglang', b'0', 'Panimbang', 'Banten', 'AKTIF', '1', '1', '1'),
('321', 'Ciamis', b'1', 'Panjalu', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('322', 'Situbondo', b'0', 'Panji', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('323', 'Kediri', b'1', 'Pare', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('324', 'Pariaman', b'0', 'Pariaman Tengah', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('325', 'Parigi Mountong', b'0', 'Parigi', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('326', 'Parigi Mountong', b'0', 'Parigi Utara', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('327', 'Ngawi', b'0', 'Paron', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('328', 'Pasaman Barat', b'0', 'Pasaman', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('329', 'Mamuju', b'0', 'Pasangkayu', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('330', 'Baubau', b'0', 'Pasarwajo', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('331', 'Purwakarta', b'1', 'Pasawahan', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('332', 'Bandung', b'0', 'Pasir Jambu', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('333', 'Subang', b'0', 'Pasirkareumbi', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('334', 'Jember', b'0', 'Patrang', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('335', 'Indramayu', b'0', 'Patrol', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('336', 'Takalar', b'0', 'Pattallassang', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('337', 'Padang', b'0', 'Pauh', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('338', 'Payakumbuh', b'0', 'Payakumbuh Barat', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('339', 'Jepara', b'0', 'Pecangaan', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('340', 'Deli Serdang', b'0', 'Pecut Sei Tuan', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('341', 'Karawang', b'0', 'Pedes', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('342', 'Lampung Timur', b'0', 'Pekalongan', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('343', 'Pemalang', b'0', 'Pemalang Kota', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('344', 'Lampung Selatan', b'0', 'Penengahan', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('345', 'Serdang Bedagai', b'0', 'Perbaungan', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('346', 'Kebumen', b'0', 'Petanahan', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('347', 'Pemalang', b'0', 'Petarukan', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('348', 'Magetan', b'0', 'Plaosan', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('349', 'Kediri', b'1', 'Plemahan', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('350', 'Jombang', b'0', 'Ploso', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('351', 'Tuban', b'0', 'Plumpang', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('352', 'Pasuruan', b'0', 'Pohjentrek', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('353', 'Area WO', b'0', 'Point WO', '', 'AKTIF', '1', '1', '1'),
('354', 'Klaten', b'1', 'Polanharjo', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('355', 'Polman', b'0', 'Polewali', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('356', 'Kolaka', b'0', 'Pomalaa', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('357', 'Konawe', b'0', 'Pondidaha', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('358', 'Poso', b'0', 'Poso Kota', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('359', 'Poso', b'0', 'Poso Kota Selatan', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('360', 'Poso', b'0', 'Poso Pesisir Utara', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('361', 'Ogan Ilir', b'0', 'Prabumulih', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('362', 'Wonogiri', b'0', 'Pracimantoro', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('363', 'Ponorogo', b'0', 'Pule', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('364', 'Ponorogo', b'0', 'Pulung', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('365', 'Banjarnegara', b'0', 'Punggelan', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('366', 'Mojokerto', b'0', 'Puri', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('367', 'Purworejo', b'1', 'Purworejo', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('368', 'Pasuruan', b'0', 'Purwosari', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('369', 'Ciamis', b'1', 'Rajadesa', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('370', 'Pemalang', b'0', 'Randu Dongkal', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('371', 'Blora', b'0', 'Randublatung', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('372', 'Lebak', b'0', 'Rangkasbitung', 'Banten', 'AKTIF', '1', '1', '1'),
('373', 'Kendari', b'0', 'Ranomeeto', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('374', 'Makassar', b'0', 'Rappocini', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('375', 'Nganjuk', b'0', 'Rejoso', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('376', 'Banyuwangi', b'0', 'Rogojampi', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('377', 'Pekanbaru', b'0', 'Rumbai', 'Riau', 'AKTIF', '1', '1', '1'),
('378', 'Ponorogo', b'0', 'Sambit', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('379', 'Blitar', b'1', 'Sananwetan', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('380', 'Sarolangun', b'0', 'Sarolangun', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('381', 'Palembang', b'0', 'Seberang Ulu I', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('382', 'Palembang', b'0', 'Seberang Ulu II', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('383', 'Gresik', b'0', 'Sedayu', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('384', 'Serdang Bedagai', b'0', 'Sei Bamban', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('385', 'Serdang Bedagai', b'0', 'Sei Rampah', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('386', 'Banyuasin', b'0', 'Sekayu', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('387', 'Yogyakarta', b'0', 'Seyegan', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('388', 'Simalungun', b'0', 'Siantar', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('389', 'Simalungun', b'0', 'Siantar Martoba', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('390', 'Pariaman', b'0', 'Sicincin', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('391', 'Cilacap', b'0', 'Sidareja', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('392', 'Lampung Selatan', b'0', 'Sidomulyo', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('393', 'Surabaya', b'0', 'Simokerto', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('394', 'Asahan', b'0', 'Simpang Empat', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('395', 'Donggala', b'0', 'Sindue', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('396', 'Sarolangun', b'0', 'Singkut', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('397', 'Malang', b'0', 'Singosari', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('398', 'Bone', b'0', 'Sinjai Utara', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('399', 'Banyumas', b'1', 'Sokaraja', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('400', 'Tuban', b'0', 'Soko', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('401', 'Pangkajene', b'0', 'Soppeng Riaja', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('402', 'Parepare', b'0', 'Soreang', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('403', 'Kebumen', b'0', 'Sruweng', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('404', 'Langkat', b'0', 'Stabat', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('405', 'Sukabumi', b'1', 'Sukaraja', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('406', 'Banyuasin', b'0', 'Sukarami', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('407', 'Purwakarta', b'0', 'Sukatani', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('408', 'Karawang', b'0', 'Sukatani 2', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('409', 'Blitar', b'0', 'Sukorejo', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('410', 'Blora', b'0', 'Sulang', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('411', 'Banyumas', b'0', 'Sumbang', 'Jawa Tengah 2', 'AKTIF', '1', '1', '1'),
('412', 'Cirebon', b'0', 'Sumber', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('413', 'Probolinggo', b'0', 'Sumberasih', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('414', 'Sumedang', b'0', 'Sumedang Utara', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('415', 'Banyuasin', b'0', 'Sungai Lilin', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('416', 'Pesisir Selatan', b'0', 'Sutera', 'Sumatera Barat', 'AKTIF', '1', '1', '1'),
('417', 'Jepara', b'0', 'Tahunan', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('418', 'Majalengka', b'1', 'Talaga', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('419', 'Banyuasin', b'0', 'Talang Kelapa', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('420', 'Tanggamus', b'0', 'Talang Padang', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('421', 'Makassar', b'0', 'Tallo', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('422', 'Blitar', b'0', 'Talun', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('423', 'Makassar', b'0', 'Tamalate', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('424', 'Jeneponto', b'0', 'Tamalatea', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('425', 'Madiun', b'0', 'Taman', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('426', 'Sidoarjo', b'0', 'Taman 1', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('427', 'Bondowoso', b'0', 'Tamanan', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('428', 'Pekanbaru', b'0', 'Tambang', 'Riau', 'AKTIF', '1', '1', '1'),
('429', 'Rokan Hulu', b'0', 'Tambusai', 'Riau', 'AKTIF', '1', '1', '1'),
('430', 'Bone', b'0', 'Tanete Riattang', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('431', 'Pangkajene', b'0', 'Tanete Rilau', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('432', 'Jember', b'1', 'Tanggul', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('433', 'Sidoarjo', b'0', 'Tanggulangin', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('434', 'Lampung Selatan', b'0', 'Tanjung Bintang', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('435', 'Deli Serdang', b'0', 'Tanjung Morawa', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('436', 'Langkat', b'0', 'Tanjung Pura', 'Sumatera Utara 2', 'AKTIF', '1', '1', '1'),
('437', 'Ogan Ilir', b'0', 'Tanjung Raja', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('438', 'Bandar Lampung', b'0', 'Tanjung Senang', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('439', 'Subang', b'0', 'Tanjung Siang', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('440', 'Batu Bara', b'0', 'Tanjung Tiram', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('441', 'Sumedang', b'1', 'Tanjungsari', 'Jawa Barat 3', 'AKTIF', '1', '1', '1'),
('442', 'Mamuju', b'0', 'Tapalang', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('443', 'Bondowoso', b'0', 'Tapen', 'Jawa Timur 5', 'AKTIF', '1', '1', '1'),
('444', 'Simalungun', b'0', 'Tapian Dolok', 'Sumatera Utara 1', 'AKTIF', '1', '1', '1'),
('445', 'Kediri', b'0', 'Tarokan', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('446', 'Tegal', b'0', 'Tarub', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('447', 'Donggala', b'0', 'Tawaeli', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('448', 'Kediri', b'0', 'Tawang', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('449', 'Kota Jambi', b'0', 'Telanaipura', 'Sumatera Selatan 1', 'AKTIF', '1', '1', '1'),
('450', 'Bandar Lampung', b'0', 'Teluk Betung Barat', 'Lampung 2', 'AKTIF', '1', '1', '1'),
('451', 'Tangerang', b'1', 'Teluknaga', 'Banten', 'AKTIF', '1', '1', '1'),
('452', 'Semarang', b'0', 'Tembalang', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('453', 'Bone', b'0', 'Tempe', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('454', 'Siak', b'0', 'Tenayan Raya', 'Riau', 'AKTIF', '1', '1', '1'),
('455', 'Semarang', b'1', 'Tengaran', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('456', 'Bogor', b'1', 'Tenjo', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('457', 'Lampung Tengah', b'0', 'Terbanggi Besar', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('458', 'Indramayu', b'0', 'Terisi', 'Jawa Barat 2', 'AKTIF', '1', '1', '1'),
('459', 'Lampung Tengah', b'0', 'Terusan Nyunyai', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('460', 'Konawe', b'0', 'Tongauna', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('461', 'Mamuju', b'0', 'Topoyo', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('462', 'Grobogan', b'0', 'Toroh', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('463', 'Parigi Mountong', b'0', 'Torue', 'Sulawesi Tengah', 'AKTIF', '1', '1', '1'),
('464', 'Mojokerto', b'0', 'Trawas', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('465', 'Ponorogo', b'0', 'Trenggalek Kota', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('466', 'Lampung Tengah', b'0', 'Trimurejo', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('467', 'Klaten', b'0', 'Trucuk', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('468', 'Siak', b'0', 'Tualang', 'Riau', 'AKTIF', '1', '1', '1'),
('469', 'Tuban', b'0', 'Tuban', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('470', 'Lubuklinggau', b'0', 'Tugumulyo', 'Sumatera Selatan 2', 'AKTIF', '1', '1', '1'),
('471', 'Tulang Bawang', b'0', 'Tulang Bawang Tengah', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('472', 'Sidoarjo', b'0', 'Tulangan', 'Jawa Timur 1', 'AKTIF', '1', '1', '1'),
('473', 'Malang', b'0', 'Tumpang', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('474', 'Semarang', b'1', 'Tuntang', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('475', 'Malang', b'0', 'Turen', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('476', 'Jeneponto', b'0', 'Ujung Bulu', 'Sulawesi Selatan 1', 'AKTIF', '1', '1', '1'),
('477', 'Palopo', b'0', 'Wara', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('478', 'Sukabumi', b'0', 'Warung Kiara', 'Jawa Barat 1', 'AKTIF', '1', '1', '1'),
('479', 'Parepare', b'0', 'Watang Sawitto', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('480', 'Yogyakarta', b'1', 'Wates', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('481', 'Konawe', b'0', 'Wawotobi', 'Sulawesi Tenggara', 'AKTIF', '1', '1', '1'),
('482', 'Lampung Timur', b'0', 'Way Jepara', 'Lampung 1', 'AKTIF', '1', '1', '1'),
('483', 'Klaten', b'0', 'Wedi', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('484', 'Ngawi', b'0', 'Widodaren', 'Jawa Timur 3', 'AKTIF', '1', '1', '1'),
('485', 'Pekalongan', b'1', 'Wiradesa', 'Jawa Tengah 3', 'AKTIF', '1', '1', '1'),
('486', 'Grobogan', b'0', 'Wirosari', 'Jawa Tengah 4', 'AKTIF', '1', '1', '1'),
('487', 'Surabaya', b'0', 'Wiyung', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('488', 'Blitar', b'0', 'Wlingi', 'Jawa Timur 2', 'AKTIF', '1', '1', '1'),
('489', 'Surabaya', b'0', 'Wonokromo', 'Jawa Timur 4', 'AKTIF', '1', '1', '1'),
('490', 'Polman', b'0', 'Wonomulyo', 'Sulawesi Selatan 2', 'AKTIF', '1', '1', '1'),
('491', 'Wonogiri', b'0', 'Wonosari', 'Jawa Tengah 1', 'AKTIF', '1', '1', '1'),
('492', 'Pesisir Selatan', b'0', 'XI Koto Tarusan', 'Sumatera Barat', 'AKTIF', '1', '1', '1');

INSERT IGNORE INTO `rencana_pemeriksaan` (`id_rencana`, `link_majelis`, `nama_rencana`, `pembuat`, `status`) 
VALUES 
('1', 'test', 'alpha', '1', '1'),
('2', 'test', 'beta', '1', '1'),
('3', 'test', 'gamma', '1', '1'),
('4', 'test', 'delta', '1', '1');

INSERT IGNORE INTO `tugas_pemeriksaan` (`id_tugas`, `tanggal_mulai`, `tanggal_selesai`, `kantor_cabang`, `pelaksana`, `rencana_pemeriksaan`) VALUES 
('1', '2020-03-24', '2020-04-01', '1', '1', '1'),
('2', '2020-04-02', '2020-04-09', '2', '1', '1'),
('3', '2020-04-10', '2020-04-17', '3', '5', '1'),
('4', '2020-05-01', '2020-05-08', '4', '5', '2'),
('5', '2020-05-09', '2020-05-16', '5', '5', '3'),
('6', '2020-05-17', '2020-05-24', '3', '5', '4');