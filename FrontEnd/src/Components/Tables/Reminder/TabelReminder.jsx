import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDatePickerButton from '../../Button/SirioDatePickerButton';
import ReminderService from '../../../Services/ReminderService';
import SirioTable from '../SirioTable';
import { withRouter, Prompt } from 'react-router-dom';
import classes from '../Rekomendasi/TabelRekomendasi.module.css';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';
import SirioWarningButton from '../../Button/ActionButton/SirioWarningButton';
import SirioAxiosBase from '../../../Services/SirioAxiosBase';

/**
 * Kelas untuk membuat komponen tabel reminder
 */
class TabelReminder extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            changeComplete: false,
            changed: false
        }

        this.renderRows = this.renderRows.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    endNotification() {
        this.setState({
            changeComplete: false
        })
    }

    handleSubmit() {
        ReminderService.submitChanges(this.props.location.state.id, this.state.rowList)
            .then(() => {
                this.renderRows()
                this.setState({
                    changeComplete: true,
                    changed: false
                })
            });
    }

    async renderRows() {
        const response = await ReminderService.getByIdRekomendasi({
            id: this.props.location.state.id
        });

        this.setState({
            rowList: response.data.result
        })
    }

    columns = [{
        dataField: 'tanggalPengiriman',
        text: 'TANGGAL',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "200px", textAlign: 'left' };
        },
        formatter: SirioAxiosBase.formatDate
    }, {
        dataField: 'noData 1',
        text: 'Aksi Ubah',
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
        text: 'Aksi Hapus',
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

    insertItem(array, action) {
        return [
            ...array.slice(),
            action
        ]
    }

    async tambah(date, id) {

        const newDate = [(date.getFullYear()), (date.getMonth() + 1), date.getDate()];

        const originalRow = this.state.rowList;

        const changedRow = this.insertItem(originalRow, {
            idReminder: Math.floor(Math.random() * 100) + 100,
            tanggalPengiriman: newDate
        })

        this.setState({
            rowList: changedRow,
            changed: true
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

        const changedDate = [(date.getFullYear()), (date.getMonth() + 1), date.getDate()];
        const originalRow = this.state.rowList;

        const changedRow = this.changeItem(originalRow, {
            idReminder: id,
            tanggalPengiriman: changedDate,
        })

        this.setState({
            rowList: changedRow,
            changed: true
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

        this.setState({
            rowList: changedRow,
            changed: true
        })
    }

    // Formatter untuk render button pertama
    getButtonsFirst(cell, row) {
        const date = row.tanggalPengiriman;
        const currentDate = new Date();
        if (date[0] < currentDate.getFullYear() || date[1] < currentDate.getMonth() || date[2] < currentDate.getDate()) {
            return "";
        }
        return (
            <SirioDatePickerButton
                purple
                id={row.idReminder}
                handleChange={(date, id) => this.ubah(date, id)}
                minDate={currentDate}
            >
                Ubah
            </SirioDatePickerButton>
        )
    }

    // Formatter untuk render button kedua
    getButtonsSecond(cell, row) {
        const date = row.tanggalPengiriman;
        const currentDate = new Date();
        if (date[0] < currentDate.getFullYear() || date[1] < currentDate.getMonth() || date[2] < currentDate.getDate()) {
            return "";
        }
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
                popper="top-end"
            >
                Tambah
            </SirioDatePickerButton>
        )
    }

    footerContent() {
        return (
            <div>
                <SirioConfirmButton
                    purple
                    classes="m-1"
                    modalTitle="Anda akan menyimpan perubahan pada tabel reminder"
                    onConfirm={this.handleSubmit}
                    customConfirmText="Konfirmasi"
                    customCancelText="Batal"
                    closeOnConfirm
                >
                    Simpan
                </SirioConfirmButton>
                <SirioWarningButton
                    red
                    modalTitle="Konfirmasi Pembatalan"
                    modalDesc="Seluruh perubahan reminder yang belum tersimpan akan dihapus. Konfirmasi?"
                    onConfirm={() => window.location.href = "/rekomendasi"}
                    customConfirmText="Konfirmasi"
                    customCancelText="Kembali"
                >
                    Batal
                </SirioWarningButton>
            </div>
        )
    }

    // Fungsi render Tabel rekomendasi
    render() {
        return (
            <>
                <SirioTable
                    title={"Daftar Reminder untuk Rekomendasi " + this.props.location.state.keterangan}
                    data={this.state.rowList}
                    id='idReminder'
                    columnsDefinition={this.columns}
                    includeSearchBar
                    headerButton={this.headerButton()}
                    footerContent={this.footerContent()}
                />
                <Prompt
                    when={this.state.changed}
                    message={`Anda akan membatalkan perubahan pengaturan, konfirmasi ?`}
                />
                {this.state.changeComplete &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Perubahan Reminder Telah Disimpan"
                        customConfirmText="Kembali"
                        onClick={this.endNotification}
                    />
                }
            </>
        );
    }
}

export default withRouter(TabelReminder);