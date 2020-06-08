import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioTable from '../SirioTable';
import { withRouter } from 'react-router-dom';
import classes from '../Rekomendasi/TabelRekomendasi.module.css';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';
import SirioWarningButton from '../../Button/ActionButton/SirioWarningButton';
import RiskLevelService from '../../../Services/RiskLevelService';
import CellEditFactory from 'react-bootstrap-table2-editor';

/**
 * Kelas untuk membuat komponen tabel reminder
 */
class TableRiskLevel extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            changeComplete: false,
            editMode: false,
            activeEditRowId: []
        }

        this.renderRows = this.renderRows.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.endNotification = this.endNotification.bind(this);
        this.toggleEditMode = this.toggleEditMode.bind(this);
        this.tambah = this.tambah.bind(this);
        this.columns = this.columns.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    endNotification() {
        this.setState({
            changeComplete: false
        })
    }

    toggleEditMode() {
        this.clearEmptyRow();
        this.setState({
            editMode: !this.state.editMode
        })
    }

    handleSubmit() {
        RiskLevelService.submitChanges(this.state.rowList)
            .then(() => {
                this.renderRows()
                this.setState({
                    changeComplete: true,
                    editMode: false,
                    rowChanged: false
                })
            });
    }

    async renderRows() {
        const response = await RiskLevelService.getAll();

        this.setState({
            rowList: response.data.result
        })
    }

    validate(newValue, row, column) {
        this.setState({
            rowChanged: true
        })
        if (newValue === "") {
            return {
                valid: false,
                message: 'Tidak boleh dikosongkan'
            }
        }
        if (column.dataField === "namaLevel") {
            if (newValue === row.namaLevel) {
                return true;
            }
            const daftarNamaLevel = this.state.rowList.map(singleRow => singleRow.namaLevel);
            if (daftarNamaLevel.includes(newValue)) {
                return {
                    valid: false,
                    message: 'Nama level harus unik'
                }
            }
        } else if (column.dataField === "bobotLevel") {
            if (newValue === row.bobotLevel) {
                return true;
            }

            // harus numerik
            if (isNaN(newValue)) {
                return {
                    valid: false,
                    message: 'Bobot level harus berupa angka'
                }
            } else if (newValue > 0) {
                return {
                    valid: false,
                    message: 'Bobot level harus dibawah nol'
                }
            }

            const daftarBobotLevel = this.state.rowList.map(singleRow => singleRow.bobotLevel);
            if (daftarBobotLevel.includes(newValue)) {
                return {
                    valid: false,
                    message: 'Bobot level harus unik'
                }
            }
        }
    }

    columns() {
        return (
            [{
                dataField: 'namaLevel',
                editable: this.state.editMode,
                text: 'Nama',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { width: "200px", textAlign: 'left' };
                },
                validator: (newValue, row, column) => {
                    return this.validate(newValue, row, column);
                }
            }, {
                dataField: 'bobotLevel',
                editable: this.state.editMode,
                text: 'Bobot',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { width: "200px", textAlign: 'left' };
                },
                validator: (newValue, row, column) => {
                    return this.validate(newValue, row, column);
                }
            }, {
                dataField: 'keteranganLevel',
                editable: this.state.editMode,
                text: 'Keterangan',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { width: "200px", textAlign: 'left' };
                },
            }, {
                dataField: 'noData 1',
                text: 'Aksi Hapus',
                editable: false,
                hidden: this.state ? !this.state.editMode : false,
                headerClasses: classes.colheader,
                classes: classes.rowItem,
                style: () => {
                    return { textAlign: 'center' }
                },
                formatter: (cell, row) => {
                    return (
                        <SirioButton
                            red
                            onClick={() => this.hapus(row.idLevel)}
                        >
                            Hapus
                        </SirioButton>
                    )
                }
            }]
        )
    }

    insertItem(array, action) {
        return [
            ...array.slice(),
            action
        ]
    }

    deleteItem(array, action) {
        const toReturn = []
        for (var row in array) {
            if (array[row].idLevel !== action.idLevel) {
                toReturn.push(array[row]);
            }
        }
        return toReturn;
    }

    async tambah() {
        const originalRow = this.state.rowList;

        const changedRow = this.insertItem(originalRow, {
            idLevel: Math.floor(Math.random() * 100) + 100,
            namaLevel: "",
            bobotLevel: "",
            keteranganLevel: ""
        })

        this.setState({
            rowList: changedRow,
            rowChanged: true
        })
    }


    async hapus(id) {
        const originalRow = this.state.rowList;

        const changedRow = this.deleteItem(originalRow, {
            idLevel: id
        })

        this.setState({
            rowList: changedRow,
            rowChanged: true
        })
    }

    hasEmptyRow() {
        const row = this.state.rowList;
        for (let i = 0; i < row.length; i++) {
            if (row[i].namaLevel === "") {
                return true;
            }
            if (row[i].bobotLevel === "") {
                return true;
            }
        }
        return false;
    }

    clearEmptyRow() {
        const row = this.state.rowList;
        const modifiedRow = [];
        for (let i = 0; i < row.length; i++) {
            if (row[i].namaLevel !== "" && row[i].bobotLevel !== "") {
                modifiedRow.push(row[i]);
            }
        }
        this.setState({
            rowList: modifiedRow
        })
    }

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        var addButton;
        if (this.state.editMode) {
            addButton = (
                <SirioButton
                    purple
                    recommended={!this.hasEmptyRow()}
                    disabled={this.hasEmptyRow()}
                    tooltip={this.hasEmptyRow() ? "Masih terdapat baris kosong" : undefined}
                    onClick={!this.hasEmptyRow() ? this.tambah : undefined}
                    classes="mx-1"
                >
                    Tambah
                </SirioButton>
            )
        }

        var toggleButton = (
            <SirioButton
                purple
                recommended
                onClick={this.toggleEditMode}
                classes="mx-1"
            >
                {this.state.editMode ? "Nonaktifkan " : "Aktifkan "} Mode Edit
            </SirioButton>
        )
        if (this.state.rowChanged) {
            toggleButton = (
                <SirioWarningButton
                    purple
                    recommended
                    modalTitle="Konfirmasi Pembatalan"
                    modalDesc="Seluruh perubahan konfigurasi Risk Level yang belum tersimpan akan dihapus. Konfirmasi?"
                    onConfirm={() => window.location.reload(false)}
                    customConfirmText="Konfirmasi"
                    customCancelText="Kembali"
                >
                    Nonaktifkan Mode Edit
                </SirioWarningButton>
            )
        }

        return (
            <>
                {toggleButton}
                {addButton}
            </>
        )
    }

    footerContent() {
        if (this.state.editMode) {
            return (
                <div className="pr-3">
                    <SirioWarningButton
                        red
                        modalTitle="Konfirmasi Pembatalan"
                        modalDesc="Seluruh perubahan konfigurasi Risk Level yang belum tersimpan akan dihapus. Konfirmasi?"
                        onConfirm={() => window.location.reload(false)}
                        customConfirmText="Konfirmasi"
                        customCancelText="Kembali"
                    >
                        Batal
                    </SirioWarningButton>
                    <SirioConfirmButton
                        purple
                        disabled={this.hasEmptyRow()}
                        tooltip={this.hasEmptyRow() ? "Masih terdapat baris yang kosong" : undefined}
                        recommended={!this.hasEmptyRow()}
                        classes="m-1"
                        modalTitle="Anda akan menyimpan perubahan konfigurasi Risk Level"
                        onConfirm={this.handleSubmit}
                        customConfirmText="Konfirmasi"
                        customCancelText="Batal"
                        disablePopUp={this.hasEmptyRow()}
                    >
                        Simpan
                    </SirioConfirmButton>

                </div>
            )
        } else return "";
    }

    cellEdit = CellEditFactory({
        mode: 'click',
        blurToSave: true,
    })

    render() {
        const column = this.columns();

        const defaultSorted = [{
            dataField: 'bobotLevel', // if dataField is not match to any column you defined, it will be ignored.
            order: 'asc' // desc or asc
        }];

        return (
            <>
                <SirioTable
                    noTotal
                    noSizePerPage
                    title={(this.state.editMode ? "Konfigurasi " : "Daftar ") + "Risk Level"}
                    subtitle={this.state.editMode && "Klik pada cell yang ingin anda ubah"}
                    data={this.state.rowList}
                    id='idLevel'
                    columnsDefinition={column}
                    includeSearchBar
                    defaultSorted={defaultSorted}
                    headerButton={this.headerButton()}
                    cellEdit={this.cellEdit}
                    indication="Belum ada Risk Level aktif yang terdaftar"
                />
                <div className="w-100 text-right">
                    {this.footerContent()}
                </div>
                {this.state.changeComplete &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Perubahan Risk Level Telah Disimpan"
                        customConfirmText="Kembali"
                        onClick={this.endNotification}
                    />
                }
            </>
        );
    }
}


export default withRouter(TableRiskLevel);