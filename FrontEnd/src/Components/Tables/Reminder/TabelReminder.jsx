import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDatePickerButton from '../../Button/SirioDatePickerButton';
import ReminderService from '../../../Services/ReminderService';
import SirioTable from '../SirioTable';
import { withRouter } from 'react-router-dom';
import classes from '../Rekomendasi/TabelRekomendasi.module.css';

/**
 * Kelas untuk membuat komponen tabel reminder
 */
class TabelReminder extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    // TBI
    async renderRows() {
        const response = await ReminderService.getByIdRekomendasi({
            id: this.props.location.state.id
        });

        console.log(response.data.result);

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
        dataField: 'tanggalPengiriman',
        text: 'TANGGAL',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        },
        formatter: this.formatDate
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
    }];

    formatDate(cell) {
        const date = cell[2];
        var month = cell[1];
        const year = cell[0]

        var monthName = ["Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"];

        month = monthName[month - 1];

        return date + " " + month + " " + year;
    }

    /**
     * Tambah, ubah, dan hapus jangan direct access ke backend, tapi simpan perubahan di cache
     * @param {} date 
     */
    static async tambah(date) {

    }

    static async ubah(date, id) {

    }

    static async hapus(id) {
        // const response = await ReminderService.delete({
        //     id: id
        // });

        // console.log(response);
    }

    // Formatter untuk render button pertama
    getButtonsFirst(cell, row) {
        return (
            <SirioButton
                purple
                onClick={(date, id) => TabelReminder.ubah(date, id)}
            >
                Ubah
            </SirioButton>
        )
    }

    // Formatter untuk render button kedua
    getButtonsSecond(cell, row) {
        return (
            <SirioButton
                red
                onClick={() => TabelReminder.hapus(row.idReminder)}
            >
                Hapus
            </SirioButton>
        );
    }

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <SirioButton
                purple
                recommended
                onClick={(date) => TabelReminder.tambah(date)}
            >
                Tambah
            </SirioButton>
        )
    }

    // Fungsi render Tabel rekomendasi
    render() {
        return (
            <SirioTable
                title={"Daftar Reminder untuk Rekomendasi " + this.props.location.state.keterangan}
                data={this.state.rowList}
                id='idReminder'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
        );
    }
}

export default withRouter(TabelReminder);