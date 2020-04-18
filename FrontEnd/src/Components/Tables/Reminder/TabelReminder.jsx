import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDatePickerButton from '../../Button/SirioDatePickerButton';
import ReminderService from '../../../Services/ReminderService';
import SirioTable from '../SirioTable';
import { withRouter, Prompt, NavLink } from 'react-router-dom';
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

        const theDate = this.props.location.state.deadline;

        this.setState({
            rowList: response.data.result,
            deadline: new Date(theDate)
        })
    }

    columns() {
        return [{
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
            text: 'Aksi Ubah Tanggal',
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
        }, {
            dataField: 'noData 3',
            text: 'Pengaturan Reminder',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            headerStyle: () => {
                return { width: "100px" }
            },
            style: () => {
                return { textAlign: 'center' }
            },
            formatter: (cell, row) => this.getPengaturanButton(cell, row)
        }];
    }

    getPengaturanButton(cell, row) {
        if (this.state.changed) {
            return (
                <SirioButton
                    purple
                    disabled
                    square
                    hover
                >
                    ➤
                </SirioButton>
            )
        }
        return (
            <NavLink
                to={{
                    pathname: "/rekomendasi/reminder/pengaturan",
                    state: {
                        idReminder: row.idReminder,
                    }
                }}>
                <SirioButton
                    purple
                    square
                    hover
                >
                    ➤
                </SirioButton>
            </NavLink>
        )
    }

    insertItem(array, action) {
        return [
            ...array.slice(),
            action
        ]
    }

    tambah(date, id) {

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

    ubah(date, id) {

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

    hapus(id) {
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
                maxDate={this.state.deadline}
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

    addDays(date, days) {
        var result = new Date(date);
        result.setDate(result.getDate() + days);
        return result;
    }

    generateHarian(hari) {
        const deadline = this.state.deadline;
        const newDateList = [];
        for (var hariIni = new Date(); hariIni < deadline; hariIni = this.addDays(hariIni, hari)) {
            const newDate = [(hariIni.getFullYear()), (hariIni.getMonth() + 1), hariIni.getDate()];
            newDateList.push({
                idReminder: Math.floor(Math.random() * 100) + 100,
                tanggalPengiriman: newDate
            })
        }
        this.setState({
            rowList: newDateList,
            changed: true
        })
    }

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <>
                <div className="d-flex flex-row align-items-center">
                    <div className="m-1 d-flex flex-row align-items-center">
                        <small className="m-1">generate Harian  </small>
                        <SirioButton
                            blue
                            hover
                            square
                            onClick={() => this.generateHarian(1)}
                        >
                            1
                        </SirioButton>
                        <SirioButton
                            blue
                            hover
                            square
                            onClick={() => this.generateHarian(2)}
                        >
                            2
                        </SirioButton>
                        <SirioButton
                            blue
                            hover
                            square
                            onClick={() => this.generateHarian(3)}
                        >
                            3
                        </SirioButton>
                    </div>
                    <div className="m-1">
                        <SirioDatePickerButton
                            purple
                            recommended
                            unchangedContent
                            handleChange={(date, id) => this.tambah(date, id)}
                            maxDate={this.state.deadline}
                            popper="top-end"
                        >
                            Tambah
                    </SirioDatePickerButton>
                    </div>
                </div>
            </>
        )
    }

    footerContent() {
        return (
            <div>
                <SirioConfirmButton
                    purple
                    hover
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
                    hover
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
        const column = this.columns();
        return (
            <>
                <SirioTable
                    title={"Daftar Reminder untuk Rekomendasi " + this.props.location.state.keterangan}
                    data={this.state.rowList}
                    id='idReminder'
                    columnsDefinition={column}
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