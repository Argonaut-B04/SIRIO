import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDatePickerButton from '../../Button/SirioDatePickerButton';
import SirioTable from '../SirioTable';
import RekomendasiService from '../../../Services/RekomendasiService';
import { NavLink } from 'react-router-dom';
import classes from './TabelRekomendasi.module.css';

/**
 * Kelas untuk membuat komponen tabel rekomendasi
 */
export default class TabelRekomendasi extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: []
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        const response = await RekomendasiService.getRekomendasiByLoggedInUser();

        var fetchedRows = [];
        response.data.result.map((entry, i) => {
            entry.no = i + 1;
            return fetchedRows.push(entry);
        })

        this.setState({
            rowList: fetchedRows
        })
    }

    /**
     * Definisi Kolom
     * - dataField          : String, parameter JSON yang akan ditampilkan. isi dengan apa saja jika tidak ingin menampilkan langsung.
     * - isDummyField       : boolean, gunakan true jika tidak ingin menampilkan String dari JSON langsung
     * - text               : String, judul kolom
     * - Sort               : boolean, mengaktifkan fungsi sort berdasarkan kolom ini
     * - classes            : cssClass, untuk assign kelas tiap cell pada kolom
     * - headerClass        : cssClass, untuk assign kelas header
     * - headerStyle        : style, untuk styling header
     * - formatter          : function, untuk manipulasi isi kolom
     */
    columns = [{
        dataField: 'no',
        text: 'NO',
        sort: true,
        classes: classes.rowNumber,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "50px", textAlign: 'center' };
        },
    }, {
        dataField: 'keterangan',
        text: 'KETERANGAN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }

    }, {
        dataField: 'status',
        text: 'STATUS',
        sort: true,
        classes: classes.rowItem,
        formatter: this.statusFormatter,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'noData 1',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtonsFirst
    }, {
        dataField: 'noData 2',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtonsSecond
    }, {
        dataField: 'noData 3',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtonsThird
    }];

    // Formatter untuk kolom status
    statusFormatter(cell) {
        switch (cell) {
            case "Draft":
                return (
                    <span style={{ color: '#7F3F98' }}>{cell}</span>
                );
            case "Menunggu Persetujuan":
                return (
                    <span style={{ color: '#8E8E8E' }}>{cell}</span>
                );
            case "Ditolak":
                return (
                    <span style={{ color: '#FF0000' }}>{cell}</span>
                );
            case "Menunggu Pelaksanaan":
                return (
                    <span style={{ color: '#5DBCD2' }}>{cell}</span>
                );
            case "Menunggu Pengaturan Tenggat Waktu":
                return (
                    <span style={{ color: '#F2994A' }}>{cell}</span>
                );
            case "Sedang Dijalankan":
                return (
                    <span style={{ color: '#6FCF97' }}>{cell}</span>
                );
            default:
                return (cell);
        }
    }

    // Formatter untuk render button pertama
    getButtonsFirst(cell, row) {
        return (
            <SirioButton
                purple
                onClick={() => window.location.href = "http://www.google.com"}
            >
                Hasil Pemeriksaan
            </SirioButton>
        )
    }

    static aturTenggatWaktu(date, id) {
        // yyyy-MM-dd
        let month = (date.getMonth() + 1);
        if (month < 10) {
            month = "0" + month;
        }
        let simpleDate = date.getDate();
        if (simpleDate < 10) {
            simpleDate = "0" + simpleDate;
        }
        let formattedDate = date.getFullYear() + "-" + month + "-" + simpleDate;
        RekomendasiService.setTenggatWaktu({
            id: id,
            tenggatWaktu: formattedDate
        }).then(response => console.log(response));
        //TODO: render ulang
    }

    // Formatter untuk render button kedua
    getButtonsSecond(cell, row) {
        const status = row.status;
        const tenggatWaktu = row.tenggatWaktu;
        const tenggatWaktuExist = tenggatWaktu !== "";
        const recommended = status === "Menunggu Pengaturan Tenggat Waktu";
        const hyperlink = status === "Menunggu Pelaksanaan" && tenggatWaktuExist;
        const text = status === "Sedang Dijalankan" && tenggatWaktuExist;

        const disabled = !recommended;
        if (recommended) {
            return (
                <SirioDatePickerButton
                    purple
                    recommended
                    id={row.id}
                    handleChange={(date, id) => TabelRekomendasi.aturTenggatWaktu(date, id)}
                >
                    Tenggat Waktu
                </SirioDatePickerButton>
            )
        } else if (hyperlink) {
            return (
                <SirioDatePickerButton
                    purple
                    hyperlink
                    id={row.id}
                    handleChange={(date, id) => TabelRekomendasi.aturTenggatWaktu(date, id)}
                >
                    {tenggatWaktuExist ? tenggatWaktu : "Tenggat Waktu"}
                </SirioDatePickerButton>
            )
        } else if (text) {
            return (
                <SirioButton
                    purple
                    text
                >
                    {tenggatWaktu}
                </SirioButton>
            )
        } else if (disabled) {
            return (
                <SirioButton
                    purple
                    disabled
                >
                    Tenggat Waktu
                </SirioButton>
            )
        }
    }

    // Formatter untuk render button ketiga
    getButtonsThird(cell, row) {
        const status = row.status;
        const reminderEnable = status === "Menunggu Pelaksanaan" || status === "Sedang Dijalankan";

        return (
            <NavLink to={{
                pathname: "/rekomendasi/reminder",
                state: {
                    id: row.id,
                    keterangan: row.keterangan
                }
            }}>
                <SirioButton
                    purple
                    disabled={!reminderEnable}
                >
                    Reminder
            </SirioButton>
            </NavLink>
        )
    }

    // Kolom yang akan di sort secara default
    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <SirioButton purple>
                Header Button
            </SirioButton>
        )
    }

    // Fungsi render Tabel rekomendasi
    render() {
        return (
            <SirioTable
                title="Daftar Rekomendasi"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
        );
    }
} 