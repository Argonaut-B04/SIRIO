# Developing SIRIO: a Code Guideline yang Santui

Ini guidelines, bukan aturan rules. Use your best judgment, and feel free to propose changes.

## Ketentuan Umum

- Gunakan 4 spasi sebagai tab 
- Gunakan camelCase sebagai pemisah kata dalam objek. 
- Untuk if-else, gunakan 1 line saja bila hanya terdapat 1 syntax dalam brace
- Gunakan Bahasa Indonesia untuk semua metode
- Comment. Please berikan komentar untuk kodingan yang cukup kompleks.

## Commit

- Usahakan ucapkan salam dalam agama commit (1 kata pembuka commit message):
    - init
    - chore
    - docs
    - feat
    - fix
- Diikuti dengan 1 kalimat singkat tentang apa saja yang berubah
> `fix`: hubungan model

> **Note**: Jangan melakukan commit untuk file yang tidak anda ubah.

- Gunakan Alur:
    - Buat branch baru
    - Work on it
    - Commit
    - Push to branch
    - Create pull request

## Pull Request

- Sebisa mungkin, gunakan template
- Jika ingin membuat pull request untuk pekerjaan di branch anda yang belum selesai, tambahkan `(WIP)` di penamaan pull request.
> `(WIP)` membuat dokumentasi
- Cantumkan referensi isu mana yang dikerjakan
- Untuk pull request yang hanya mengandung 1 commit, lakukan `Rebase and Merge Commit`

## Issues

- Tidak ada template untuk issues, tapi mohon diperjelas dan berikan label.

## Sekali Lagi, Terima Kasih untuk Tidak Deadliner

- Guideline ini bersifat opsional.
    - Tidak perlu memaksakan diri mengikuti guideline.
- Progress setiap hari akan sangat dihargai
- Frontend menggunakan React
- Jangan takut untuk bertanya