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
export default class TabelReminder extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            idRekomendasi: this.props.location.state ? this.props.location.state.id : null,
            directAccess: this.props.location.state ? true : false
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    // TBI
    async renderRows() {
        // const response = await RekomendasiService.getRekomendasiByLoggedInUser();

        // var fetchedRows = [];
        // response.data.result.map((entry, i) => {
        //     entry.no = i + 1;
        //     return fetchedRows.push(entry);
        // })

        // this.setState({
        //     rowList: fetchedRows
        // })
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
        dataField: 'tanggal',
        text: 'TANGGAL',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
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
    }];

    static tambah(date) {

    }

    static ubah(date, id) {

    }

    static hapus(id) {

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
                onClick={(id) => TabelReminder.hapus(id)}
            >
                Hapus
            </SirioButton>
        );
    }

    // Kolom yang akan di sort secara default
    defaultSorted = [{
        dataField: 'no',
        order: 'asc'
    }];

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