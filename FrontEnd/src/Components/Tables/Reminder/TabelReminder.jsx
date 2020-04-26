import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDatePickerButton from '../../Button/SirioDatePickerButton';
import ReminderService from '../../../Services/ReminderService';
import SirioTable from '../SirioTable';
import { withRouter, Prompt, NavLink, Redirect } from 'react-router-dom';
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
        if (this.props.location.state) {
            this.renderRows();
        }
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
            })
            .catch(error => {
                if (!error.response) {
                    window.location.href = "/error";
                } else {
                    console.log(error.response.data);
                    this.setState({
                        redirector: true,
                        code: error.response.data.status,
                        detail: error.response.data.message,
                    })
                }
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
            idReminder: Math.floor(Math.random() * 1000) + 1,
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
        const dateOld = new Date(date);
        const currentDate = new Date();
        if (dateOld <= currentDate) {
            return "";
        }
        const minDate = this.addDays(currentDate, 1);
        return (
            <SirioDatePickerButton
                purple
                hover
                id={row.idReminder}
                handleChange={(date, id) => this.ubah(date, id)}
                minDate={minDate}
                maxDate={this.state.deadline}
            >
                Ubah
            </SirioDatePickerButton>
        )
    }

    // Formatter untuk render button kedua
    getButtonsSecond(cell, row) {
        const date = row.tanggalPengiriman;
        const dateOld = new Date(date);
        const currentDate = new Date();
        if (dateOld <= currentDate) {
            return "";
        }
        return (
            <SirioButton
                red
                hover
                id={row.idReminder}
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
        const nextday = this.addDays(new Date(), 1);
        for (var hariIni = nextday; hariIni < deadline; hariIni = this.addDays(hariIni, hari)) {
            const newDate = [(hariIni.getFullYear()), (hariIni.getMonth() + 1), hariIni.getDate()];
            newDateList.push({
                idReminder: Math.floor(Math.random() * 1000) + 1,
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
        const minDate = this.addDays(new Date(), 1);
        return (
            <>
                <div className="d-flex flex-row align-items-center">
                    <div className="m-1">
                        <SirioDatePickerButton
                            purple
                            recommended
                            unchangedContent
                            handleChange={(date, id) => this.tambah(date, id)}
                            minDate={minDate}
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

    searchLeftButton() {
        return (
            <div className="d-flex flex-row align-items-center">
                <p className="m-1">Reminder Harian </p>
                <div>
                    <SirioButton
                        purple
                        hover
                        square
                        onClick={() => this.generateHarian(1)}
                        classes={classes.sizeperpage}
                    >
                        1
                        </SirioButton>
                    <SirioButton
                        purple
                        hover
                        square
                        onClick={() => this.generateHarian(2)}
                        classes={classes.sizeperpage}
                    >
                        2
                        </SirioButton>
                    <SirioButton
                        purple
                        hover
                        square
                        onClick={() => this.generateHarian(3)}
                        classes={classes.sizeperpage}
                    >
                        3
                        </SirioButton>
                </div>
            </div>
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

    render() {
        if (!this.props.location.state) {
            return (
                <Redirect to={{
                    pathname: "/error",
                    state: {
                        detail: "Halaman ini tidak dapat diakses secara langsung dari URL",
                        code: "401"
                    }
                }} />
            )
        }

        const { detail, code, redirector, rowList, changed, changeComplete } = this.state;
        if (redirector) {
            return (
                <Redirect to={{
                    pathname: "/error",
                    state: {
                        detail: detail,
                        code: code
                    }
                }} />
            )
        }

        const { keterangan } = this.props.location.state;
        var keteranganHeader = keterangan.length > 20 ? keterangan.substring(0, 20) + "..." : keterangan;
        const column = this.columns();
        return (
            <>
                <SirioTable
                    title={"Daftar Reminder untuk Rekomendasi " + keteranganHeader}
                    data={rowList}
                    leftSearch={this.searchLeftButton()}
                    id='idReminder'
                    columnsDefinition={column}
                    includeSearchBar
                    headerButton={this.headerButton()}
                    footerContent={this.footerContent()}
                />
                <Prompt
                    when={changed}
                    message={`Anda akan membatalkan perubahan pengaturan, konfirmasi ?`}
                />
                {changeComplete &&
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