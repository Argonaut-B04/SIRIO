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
        this.hapus = this.hapus.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    handleSubmit() {
        ReminderService.submitChanges(this.state.rowList);
    }

    async renderRows() {
        const response = await ReminderService.getByIdRekomendasi({
            id: this.props.location.state.id
        });

        var fetchedRows = [];
        response.data.result.map((entry, i) => {
            entry.no = i + 1;
            return fetchedRows.push(entry);
        })

        this.setState({
            rowList: fetchedRows
        })
    }

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
            return { width: "200px", textAlign: 'left' };
        },
        formatter: this.formatDate
    }, {
        dataField: 'noData 1',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        headerStyle: () => {
            return { width: "200px" }
        },
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: (cell, row) => this.getButtonsFirst(cell, row)
    }, {
        dataField: 'noData 2',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        headerStyle: () => {
            return { width: "200px" }
        },
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: (cell, row) => this.getButtonsSecond(cell, row)
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

    insertItem(array, action) {
        return [
            ...array.slice(),
            action
        ]
    }

    async tambah(date, id) {

        const newDate = [(date.getFullYear()), date.getMonth(), date.getDate()];

        const originalRow = this.state.rowList;

        const changedRow = this.insertItem(originalRow, {
            idReminder: Math.floor(Math.random() * 100) + 100,
            tanggalPengiriman: newDate
        })

        this.setState({
            rowList: changedRow
        })
    }

    changeItem(array, action) {
        return array.map(row => {
            if (row.idReminder === action.idReminder) {
                action.no = row.no;
                return action;
            }
            return row;
        })
    }

    async ubah(date, id) {

        const changedDate = [(date.getFullYear()), date.getMonth(), date.getDate()];

        const originalRow = this.state.rowList;

        const changedRow = this.changeItem(originalRow, {
            idReminder: id,
            tanggalPengiriman: changedDate,
        })

        this.setState({
            rowList: changedRow
        })
    }

    deleteItem(array, action) {
        const toReturn = []
        for (var row in array) {
            if (array[row].idReminder !== action.idReminder) {
                toReturn.push(array[row]);
            }
        }
        return toReturn;
    }

    async hapus(id) {
        const originalRow = this.state.rowList;

        const changedRow = this.deleteItem(originalRow, {
            idReminder: id
        })

        console.log(changedRow);

        this.setState({
            rowList: changedRow
        })
    }

    // Formatter untuk render button pertama
    getButtonsFirst(cell, row) {
        return (
            <SirioDatePickerButton
                purple
                id={row.idReminder}
                handleChange={(date, id) => this.ubah(date, id)}
            >
                Ubah
            </SirioDatePickerButton>
        )
    }

    // Formatter untuk render button kedua
    getButtonsSecond(cell, row) {
        return (
            <SirioButton
                red
                onClick={() => this.hapus(row.idReminder)}
            >
                Hapus
            </SirioButton>
        );
    }

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <SirioDatePickerButton
                purple
                recommended
                unchangedContent
                handleChange={(date, id) => this.tambah(date, id)}
            >
                Tambah
            </SirioDatePickerButton>
        )
    }

    footerContent() {
        return (
            <div>
                <SirioButton purple classes="m-1" onClick={this.handleSubmit}>
                    Simpan
                </SirioButton>
                <SirioButton red classes="m-1">
                    Batal
                </SirioButton>
            </div>
        )
    }

    // Fungsi render Tabel rekomendasi
    render() {
        console.log(this.state);
        return (
            <SirioTable
                title={"Daftar Reminder untuk Rekomendasi " + this.props.location.state.keterangan}
                data={this.state.rowList}
                id='idReminder'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
                footerContent={this.footerContent()}
            />
        );
    }
}

export default withRouter(TabelReminder);