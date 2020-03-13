import React from 'react';
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from 'react-bootstrap-table2-paginator';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import overlayFactory from 'react-bootstrap-table2-overlay';
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css"
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import "./TabelRekomendasi.module.css";

/**
 * Kelas untuk tabel rekomendasi.
 * Mungkin akan digeneralisasi sebagai kelas tabel (nanti).
 */
export default class TabelRekomendasi extends React.Component {

    /**
     * Apa yang akan ditampilkan bila tidak terdapat data dalam tabel.
     */
    indication() {
        return "No Data in Table"
    }

    /**
     * Formatter untuk format spesifik cell dengan ketentuan tertentu.
     * Sebagai contoh untuk memberi warna merah pada {cell} jika konten {cell} adalah ditolak.
     * Ini masih template dan akan diubah sesuai kebutuhan nantinya.
     * Parameter merupakan bawaan dari modul sehingga otomatis terisi saat fungsi dipanggil.
     * 
     * @param {cell} konten dari cell 
     * @param {row} baris yang dipilih
     */
    priceFormatter(cell, row) {
        if (cell === "10000") {
            return (
                <span>
                    <strong style={{ color: 'red' }}>$ {cell} NTD(Sales!!)</strong>
                </span>
            );
        }
        return (
            <span>$ {cell} NTD</span>
        );
    }

    /**
     * BUGGED
     * Formatter untuk membuat nomor baris.
     * Parameter merupakan bawaan dari modul sehingga otomatis terisi saat fungsi dipanggil.
     * Parameter rowIndex merupakan parameter ke 3, jadi jangan hapus parameter cell dan row meskipun tidak digunakan.
     * 
     * @param {rowIndex} index baris saat ini. 
     */
    rowNumber(cell, row, rowIndex) {
        return (
            <>{rowIndex + 1}</>
        )
    }

    /**
     * Formatter untuk membuat kolom 'action'.
     * Pada fungsi ini saya membuat isi cell di setiap baris untuk setiap kolom 'action' adalah tombol.
     * Parameter merupakan bawaan dari modul sehingga otomatis terisi saat fungsi dipanggil.
     */
    getButtons() {
        return (
            <div className="d-flex align-items-center justify-content-around">
                <a className="btn-sirio-primary" href="/hasil">Hasil Pemeriksaan</a>
                <a className="btn-sirio-primary" href="https://www.google.com/">Tenggat Waktu</a>
                <a className="btn-sirio-primary" href="https://www.google.com/">Reminder</a>
            </div>
        )
    }

    /**
     * Variabel columns menjelaskan deskripsi isi dari setiap column dalam list.
     * 
     * dataField merupakan "key" dari json object yang akan ditampilkan "value" nya.
     * text merupakan isi dari header kolom.
     * headerStyle merupakan styling header kolom.
     * formatter merupakan nama fungsi yang akan dijalankan pada setiap baris di kolom ini.
     * sort merupakan boolean, jika true, maka tabel dapat di sort menurut kolom ini.
     */
    columns = [{
        text: 'NO',
        sort: true,
        headerStyle: () => {
            return { width: "50px", textAlign: 'center' };
        },
        formatter: this.rowNumber
    }, {
        dataField: 'namaRekomendasi',
        text: 'NAMA',
        sort: true,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }

    }, {
        dataField: 'statusRekomendasi',
        text: 'STATUS',
        sort: true,
        formatter: this.priceFormatter,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'id',
        formatter: this.getButtons
    }];

    /**
     * Variabel ini merupakan isi dari konten dengan jenis List yang akan ditampilkan menurut variabel columns.
     */
    products = [
        { "id": 10, "namaRekomendasi": "Nathan", "statusRekomendasi": 10000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
        { "id": 2, "namaRekomendasi": "Nathan2", "statusRekomendasi": 12000 },
        { "id": 1, "namaRekomendasi": "Nathan1", "statusRekomendasi": 110000 },
    ]

    /**
     * Variabel ini menjelaskan sort secara default.
     */
    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    /**
     * Fungsi ini menjelaskan statistik penampilan data di tabel.
     * Misal: menampilkan data ke 1 sampai ke 10 dari 16 entries
     */
    customTotal(from, to, size) {
        return (
            <span className="react-bootstrap-table-pagination-total text-secondary">
                Show {from} to {to} of {size} entries
            </span>
        )
    }

    /**
     * Fungsi ini digunakan untuk kustomisasi tombol paginasi.
     * Belum selesai diimplementasi.
     */
    pageButtonRenderer = ({
        page,
        active,
        disabled,
        title,
        onPageChange
    }) => {
        const handleClick = (e) => {
            e.preventDefault();
            onPageChange(page);
        };
        return (
            //TODO: bikin jadi komponen baru
            <li className="page-item">
                <a href="https://www.google.com/" className="sirio-pagination-button" onClick={handleClick}>{page}</a>
            </li>
        );
    };

    /**
     * Variabel ini menjelaskan tentang pagination.
     */
    pagination = paginationFactory({
        sizePerPage: 10,
        prePageText: "Previous",
        nextPageText: "Next",
        showTotal: true,
        paginationTotalRenderer: this.customTotal,
        // pageButtonRenderer: this.pageButtonRenderer
    })

    /**
     * Variabel ini menjelaskan tentang overlay yaitu tampilan tabel ketika data sedang diambil atau diproses.
     */
    overlay = overlayFactory({
        spinner: true,
        styles: {
            overlay: (base) => ({
                ...base,
                background: 'rgba(255, 0, 0, 0.5)'
            })
        }
    })

    /**
     * Render sudah tau lah ya, return utama dari kelas ini
     */
    render() {
        // Masukan seluruh variabel kelas kedalam render, masih belum best practice, but it will suffice for now.
        const { indication, products, columns, defaultSorted, pagination, overlay } = this;
        const { SearchBar } = Search;
        return (
            <>
                {/* Judul Tabel */}
                <h1>Daftar Rekomendasi</h1>

                {/* Tabel dimasukan kedalam ToolkitProvider untuk keperluan search */}
                {/* Parameter data tabel dimasukan kedalam Toolkit Provider */}
                {/* Kalau bingung, worth banget baca dokumentasi ToolkitProvider */}
                <ToolkitProvider
                    bootstrap4
                    keyField='id'
                    data={products}
                    columns={columns}
                    search
                >
                    {
                        props => (
                            <div>
                                <div className="text-right">
                                    <label className="m-2">Search: </label>
                                    <SearchBar
                                        {...props.searchProps}
                                        placeholder
                                    />
                                </div>
                                <BootstrapTable
                                    {...props.baseProps}
                                    ref={n => this.node = n}
                                    striped
                                    hover
                                    condensed
                                    noDataIndication={indication}
                                    defaultSorted={defaultSorted}
                                    pagination={pagination}
                                    overlay={overlay}
                                />
                            </div>
                        )
                    }
                </ToolkitProvider>
            </>
        );
    }
} 