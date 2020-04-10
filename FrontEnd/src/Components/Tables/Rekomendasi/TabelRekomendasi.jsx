import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioDatePickerButton from '../../Button/SirioDatePickerButton';
import SirioTable from '../SirioTable';
import RekomendasiService from '../../../Services/RekomendasiService';
import { NavLink } from 'react-router-dom';
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
            changeComplete: false
        }

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        const response = await RekomendasiService.getRekomendasiByLoggedInUser();

        this.setState({
            rowList: response.data.result
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
            text: '',
            headerClasses: [classes.colheader, "d-block d-sm-table-cell"].join(" "),
            classes: [classes.rowItem, "d-block d-sm-table-cell"].join(" "),
            style: () => {
                return { textAlign: 'center' }
            },
            formatter: (cell, row) => this.getButtonsFirst(cell, row)
        }, {
            dataField: 'noData 2',
            text: '',
            headerClasses: [classes.colheader, "d-block d-sm-table-cell"].join(" "),
            classes: [classes.rowItem, "d-block d-sm-table-cell"].join(" "),
            style: () => {
                return { textAlign: 'center' }
            },
            formatter: (cell, row) => this.getButtonsSecond(cell, row)
        }, {
            dataField: 'noData 3',
            text: '',
            headerClasses: [classes.colheader, "d-block d-sm-table-cell"].join(" "),
            classes: [classes.rowItem, "d-block d-sm-table-cell"].join(" "),
            style: () => {
                return { textAlign: 'center' }
            },
            formatter: (cell, row) => this.getButtonsThird(cell, row)
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
            <SirioButton
                purple
                onClick={() => alert("Halaman Hasil Pemeriksaan belum terimplementasi")}
            >
                Hasil Pemeriksaan
            </SirioButton>
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
        });
    }

    // Formatter untuk render button kedua
    getButtonsSecond(cell, row) {
        const status = row.status;
        const tenggatWaktu = SirioAxiosBase.formatDateFromString(row.tenggatWaktu);
        const recommended = status === "Menunggu Pengaturan Tenggat Waktu";
        const hyperlink = status === "Menunggu Pelaksanaan";
        const text = status === "Sedang Dilaksanakan" || status === "Selesai";

        const disabled = !recommended;
        if (text) {
            return (
                <SirioButton
                    purple
                    text
                >
                    {tenggatWaktu}
                </SirioButton>
            )
        } else if (hyperlink) {
            return (
                <SirioDatePickerButton
                    purple
                    hyperlink
                    id={row.id}
                    handleChange={(date, id) => this.aturTenggatWaktu(date, id)}
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
                    handleChange={(date, id) => this.aturTenggatWaktu(date, id)}
                >
                    Tenggat Waktu
                </SirioDatePickerButton>
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

        if (reminderEnable) {
            return (
                <NavLink
                    to={{
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
        } else {
            return (
                <SirioButton
                    purple
                    disabled
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
        return (
            <>
                <SirioTable
                    title="Daftar Rekomendasi"
                    data={this.state.rowList}
                    defaultSorted={this.defaultSorted}
                    id='id'
                    columnsDefinition={this.columns}
                    includeSearchBar
                />
                {this.state.changeComplete &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Tenggat Waktu berhasil Disimpan"
                        customConfirmText="Kembali"
                        onClick={this.endNotification}
                    />
                }
            </>
        );
    }
} 