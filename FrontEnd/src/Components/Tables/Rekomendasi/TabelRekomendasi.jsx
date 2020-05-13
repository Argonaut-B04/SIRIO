import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDatePickerButton from '../../Button/SirioDatePickerButton';
import SirioTable from '../SirioTable';
import RekomendasiService from '../../../Services/RekomendasiService';
import { NavLink, Redirect } from 'react-router-dom';
import classes from './TabelRekomendasi.module.css';
import SirioAxiosBase from '../../../Services/SirioAxiosBase';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';

/**
 * Kelas untuk membuat komponen tabel rekomendasi
 */
export default class TabelRekomendasi extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            changeComplete: false,
        }

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        RekomendasiService.getRekomendasiByLoggedInUser()
            .then(response => {

                // Mengubah isi dari loader
                this.props.changeLoadingBody("Menampilkan data");
                this.setState({
                    rowList: response.data.result
                })
            })
            .catch((error) => {
                if (!error.response) {
                    window.location.href = "/error";
                } else {
                    this.setState({
                        redirector: true,
                        code: error.response.data.status,
                        detail: error.response.data.message,
                    })
                }
            });

        this.props.contentFinishLoading(); // Setelah jeda waktu, hentikan loader
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
    columns = [
        {
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
            sortFunc: (a, b, order, dataField, rowA, rowB) => {
                const sorter = {
                    "Draft": 1,
                    "Menunggu Persetujuan": 2,
                    "Ditolak": 3,
                    "Menunggu Pengaturan Tenggat Waktu": 4,
                    "Menunggu Pelaksanaan": 5,
                    "Sedang Dilaksanakan": 6,
                    "Selesai": 7
                }
                const aNum = sorter[a];
                const bNum = sorter[b];
                if (aNum === bNum) {
                    return rowA.keterangan.localeCompare(rowB.keterangan);
                }
                if (order === "asc") {
                    return aNum - bNum;
                } else {
                    return bNum - aNum;
                }
            },
            classes: classes.rowItem,
            formatter: this.statusFormatter,
            headerClasses: classes.colheader,
            headerStyle: () => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'noData 1',
            text: 'Hasil Pemeriksaan',
            headerClasses: [classes.colheader, "d-block d-sm-table-cell"].join(" "),
            classes: [classes.rowItem, "d-block d-sm-table-cell"].join(" "),
            style: () => {
                return { textAlign: 'center' }
            },
            formatter: (cell, row) => this.getButtonsFirst(cell, row)
        }, {
            dataField: 'noData 2',
            text: 'Tenggat Waktu',
            headerClasses: [classes.colheader, "d-block d-sm-table-cell"].join(" "),
            classes: [classes.rowItem, "d-block d-sm-table-cell"].join(" "),
            style: () => {
                return { textAlign: 'center' }
            },
            sort: true,
            sortFunc: (a, b, order, dataField, rowA, rowB) => {
                const tenggatWaktuA = Date.parse(rowA.tenggatWaktu);
                const tenggatWaktuB = Date.parse(rowB.tenggatWaktu);
                if (isNaN(tenggatWaktuA) && isNaN(tenggatWaktuB)) {
                    return rowA.keterangan.localeCompare(rowB.keterangan);
                } else if (isNaN(tenggatWaktuA)) {
                    return 1
                } else if (isNaN(tenggatWaktuB)) {
                    return -1
                }
                if (order === "asc") {
                    return tenggatWaktuA - tenggatWaktuB;
                } else {
                    return tenggatWaktuB - tenggatWaktuA;
                }
            },
            formatter: (cell, row) => this.getButtonsSecond(cell, row)
        }, {
            dataField: 'noData 3',
            text: 'Reminder',
            headerClasses: [classes.colheader, "d-block d-sm-table-cell"].join(" "),
            classes: [classes.rowItem, "d-block d-sm-table-cell"].join(" "),
            style: () => {
                return { textAlign: 'center' }
            },
            formatter: (cell, row) => this.getButtonsThird(cell, row)
        }, {
            dataField: 'untukSearchStatus',
            text: '',
            hidden: true,
            formatter: this.statusNoFormatter,
        }, {
            dataField: 'untukSearchTanggal',
            text: '',
            hidden: true,
            formatter: this.tanggalNoFormatter,
        }];

    tanggalNoFormatter(cell, row) {
        return SirioAxiosBase.formatDateFromString(row.tenggatWaktu);
    }

    statusNoFormatter(cell, row) {
        return row.status;
    }

    // Formatter untuk kolom status
    statusFormatter(cell, row) {
        switch (row.status) {
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
            case "Sedang Dilaksanakan":
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
            <NavLink
                to={{
                    pathname: "/hasil-pemeriksaan/detail",
                    state: {
                        id: row.idHasilPemeriksaan,
                    }
                }}>
                <SirioButton
                    purple
                    hover
                    tooltip="Pergi ke halaman hasil pemeriksaan dari rekomendasi ini"
                >
                    Hasil Pemeriksaan
                </SirioButton>
            </NavLink>
        )
    }

    aturTenggatWaktu(date, id) {
        const newDate = [(date.getFullYear()), (date.getMonth() + 1), date.getDate()];
        RekomendasiService.setTenggatWaktu({
            id: id,
            tenggatWaktuDate: newDate
        }).then(() => {
            this.renderRows();
            this.setState({
                changeComplete: true
            })
        }).catch(error => console.log(error.response.data));
    }

    // Formatter untuk render button kedua
    getButtonsSecond(cell, row) {
        const status = row.status;
        const tenggatWaktu = row.tenggatWaktuDate ? SirioAxiosBase.formatDate(row.tenggatWaktuDate) : "Tenggat Waktu";
        const tenggatWaktuDate = row.tenggatWaktuDate ? new Date(row.tenggatWaktuDate) : null;
        const recommended = status === "Menunggu Pengaturan Tenggat Waktu";
        const hyperlink = status === "Menunggu Pelaksanaan";
        const text = status === "Selesai" || status === "Sedang Dilaksanakan";

        const disabled = !recommended;
        if (text) {
            return (
                <SirioButton
                    purple
                    text
                    tooltip="Tidak dapat mengatur tenggat waktu untuk rekomendasi yang sedang atau sudah dijalankan"
                >
                    {tenggatWaktu}
                </SirioButton>
            )
        } else if (hyperlink) {
            return (
                <SirioDatePickerButton
                    purple
                    hover
                    hyperlink
                    id={row.id}
                    selectedDate={tenggatWaktuDate}
                    handleChange={(date, id) => this.aturTenggatWaktu(date, id)}
                    tooltip="Ubah tenggat waktu"
                >
                    {tenggatWaktu}
                </SirioDatePickerButton>
            )
        } else if (recommended) {
            return (
                <SirioDatePickerButton
                    purple
                    recommended
                    id={row.id}
                    selectedDate={tenggatWaktuDate}
                    handleChange={(date, id) => this.aturTenggatWaktu(date, id)}
                    tooltip="Atur tenggat waktu"
                >
                    Tenggat Waktu
                </SirioDatePickerButton>
            )
        } else if (disabled) {
            return (
                <SirioButton
                    purple
                    disabled
                    tooltip="Tenggat waktu belum dapat diatur"
                >
                    Tenggat Waktu
                </SirioButton>
            )
        }
    }

    // Formatter untuk render button ketiga
    getButtonsThird(cell, row) {
        const status = row.status;
        const reminderEnable = status === "Menunggu Pelaksanaan" || status === "Sedang Dilaksanakan";

        if (reminderEnable) {
            return (
                <NavLink
                    to={{
                        pathname: "/rekomendasi/reminder",
                        state: {
                            id: row.id,
                            keterangan: row.keterangan,
                            deadline: row.tenggatWaktuDate
                        }
                    }}>
                    <SirioButton
                        purple
                        hover
                        tooltip="Buka pengaturan reminder"
                    >
                        Reminder
                    </SirioButton>
                </NavLink>
            )
        } else {
            return (
                <SirioButton
                    purple
                    disabled
                    tooltip="Reminder belum dapat diatur"
                >
                    Reminder
                </SirioButton>
            )
        }
    }

    // Kolom yang akan di sort secara default
    defaultSorted = [{
        dataField: 'status',
        order: 'asc'
    }];

    endNotification() {
        this.setState({
            changeComplete: false
        })
    }

    // Fungsi render Tabel rekomendasi
    render() {
        const { defaultSorted, columns, endNotification, state } = this;
        const { redirector, rowList, changeComplete, detail, code } = state;

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
        return (
            <>
                <SirioTable
                    title="Daftar Rekomendasi"
                    data={rowList}
                    defaultSorted={defaultSorted}
                    id='id'
                    columnsDefinition={columns}
                    includeSearchBar
                    indication="Tidak Terdapat Data Rekomendasi"
                />
                {changeComplete &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Tenggat Waktu berhasil Disimpan"
                        customConfirmText="Kembali"
                        onClick={endNotification}
                    />
                }
            </>
        );
    }
} 