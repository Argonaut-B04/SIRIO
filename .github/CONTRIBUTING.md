# Developing SIRIO: a Code Guideline yang Santui

Ini guidelines, bukan aturan rules. Use your best judgment, and feel free to propose changes.

## Ketentuan Umum

- Gunakan 4 spasi sebagai tab 
> Kalau intellij, ini udah default. Jadi aman `ヾ(•ω• ')o` 
- Untuk nama variabel dengan kelas bawaan Java (int, String, etc), gunakan underscore sebagai pemisah kata. 
> `int` jumlah_mata_kita_saling_ketemu
- Untuk nama variabel dengan kelas yang dibuat SIRIO (Rekomendasi, StatusReminder, etc) gunakan camelCase sebagai pemisah kata. 
> `Reminder` reminderThatYouArePrecious
- Untuk if-else, gunakan 1 line saja bila hanya terdapat 1 syntax dalam brace
> if (`sirioKelar`) return `kuyMakanMakan`
- Comment. Please berikan komentar untuk kodingan yang cukup kompleks, yang menurut kamu bisa bikin orang pusing, mual - mual, diare, panas tinggi, sesak napas, sariawan, bibir pecah - pecah, minum adem sari.

## Commit

- Usahakan ucapkan salam dalam agama commit (1 kata pembuka commit message):
    - init
    - chore
    - docs
    - feat
    - fix
- Diikuti dengan 1 kalimat singkat tentang apa saja yang berubah
> `fix`: hubungan kita

> **Note**: Jangan melakukan commit untuk file yang tidak anda ubah (dengan kata lain, jangan `git add .`)

- Please jangan push ke master, berat, gunakan alur ini saja:
    - Bikin branch baru
    - kerja
    - commit
    - push ke branch
    - buat pull request

## Pull Request

- Sebisa mungkin, gunakan template... Thanks `(_　_)`
- Jika ingin membuat pull request untuk pekerjaan di branch anda yang belum selesai, tambahkan `(WIP)` di penamaan pull request.
> `(WIP)` bikin dokumentasi cerita kita berdua
- Cantumkan referensi isu mana yang dikerjakan
- Untuk pull request yang hanya mengandung 1 commit, lakukan `Rebase and Merge Commit`

## Issues

- Tidak ada template untuk issues, tapi mohon diperjelas dan berikan label. `ヾ(≧▽≦*)o`

## Sekali Lagi, Terima Kasih untuk Tidak Deadliner

- Guideline ini bersifat opsional.
    - Kalau membebani anda dalam ngoding, sabi ngoding saja seenjoy anda. 
    - Tapi aku minta ijin buat `sentuh` kodingannya buat `styling` saja
- Usahakan ada progress tiap hari
- Jangan lupa belajar react lagi
- Jangan takut untuk bertanya, kalau nda ada yang bisa jawab, kita belajar bareng `（￣︶￣）↗`
- Jangan stress, kalau udah mentok mungkin sabi buka Twitter dulu atau dengerin lagu `( ´･･)ﾉ(._. )`
