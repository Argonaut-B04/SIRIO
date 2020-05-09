import React from 'react';
import SirioButton from '../../Button/SirioButton';
import SirioTable from '../SirioTable';
import { withRouter } from 'react-router-dom';
import classes from '../Rekomendasi/TabelRekomendasi.module.css';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';
import SirioWarningButton from '../../Button/ActionButton/SirioWarningButton';
import RiskRatingService from '../../../Services/RiskRatingService';
import CellEditFactory from 'react-bootstrap-table2-editor';
import SirioComponentHeader from '../../Header/SirioComponentHeader';
import ComponentWrapper from '../../../Layout/ComponentWrapper';

/**
 * Kelas untuk membuat komponen tabel risk rating
 */
class TableRiskRating extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            changeComplete: false,
            editMode: false,
            error: []
        }

        this.renderRows = this.renderRows.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.endNotification = this.endNotification.bind(this);
        this.toggleEditMode = this.toggleEditMode.bind(this);
        this.tambah = this.tambah.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    componentDidUpdate() {
        if (this.state.editMode && !this.hasEmptyRow()) {
            this.validateRange();
        }
    }

    endNotification() {
        this.setState({
            changeComplete: false,
            rowChanged: false,
        })
    }

    toggleEditMode() {
        this.setState({
            editMode: !this.state.editMode
        })
    }

    handleSubmit() {
        RiskRatingService.submitChanges(this.state.rowList)
            .then(() => {
                this.renderRows()
                this.setState({
                    changeComplete: true,
                    editMode: false
                })
            });
    }

    async renderRows() {
        const response = await RiskRatingService.getAll();

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
        if (column.dataField === "namaRating") {
            if (newValue === row.namaRating) {
                return true;
            }
            const daftarNamaLevel = this.state.rowList.map(singleRow => singleRow.namaRating);
            if (daftarNamaLevel.includes(newValue)) {
                return {
                    valid: false,
                    message: 'Nama rating harus unik'
                }
            }
        } else if (column.dataField === "skorMinimal") {
            if (newValue === row.skorMinimal) {
                return true;
            }

            // harus numerik
            if (isNaN(newValue)) {
                return {
                    valid: false,
                    message: 'Skor Minimal harus berupa angka'
                }
            }

            if (parseInt(newValue) < parseInt(row.skorMaksimal)) {
                return {
                    valid: false,
                    message: "Skor minimal tidak boleh lebih banyak daripada skor maksimal"
                }
            }

            if (0 > parseInt(newValue)) {
                return {
                    valid: false,
                    message: "Skor minimal tidak boleh kurang dari 0"
                }
            }

        } else if (column.dataField === "skorMaksimal") {
            if (newValue === row.skorMinimal) {
                return true;
            }

            // harus numerik
            if (isNaN(newValue)) {
                return {
                    valid: false,
                    message: 'Skor maksimal harus berupa angka'
                }
            }

            if (parseInt(row.skorMinimal) > parseInt(newValue)) {
                return {
                    valid: false,
                    message: "Skor Maksimal tidak boleh lebih sedikit daripada skor minimal"
                }
            }

            if (100 < parseInt(newValue)) {
                return {
                    valid: false,
                    message: "Skor Maksimal tidak boleh melebihi 100"
                }
            }
        }
    }

    validateRange() {
        const daftarRow = this.state.rowList;
        const arrayOfOne = Array(101).fill(0);

        var errorResult = [];

        // cek yang tabrakan
        for (let i = 0; i < daftarRow.length; i++) {
            const skorMinimal = daftarRow[i].skorMinimal;
            const skorMaksimal = daftarRow[i].skorMaksimal;
            for (let j = skorMinimal; j <= skorMaksimal; j++) {
                if (arrayOfOne[j] === 0) {
                    arrayOfOne[j] = 1;
                } else {
                    if (!errorResult.includes("Jangkauan skor saling bertabrakan")) {
                        errorResult.push("Jangkauan skor saling bertabrakan");
                    }
                }
            }
        }

        // cek kalau ada yang belum termasuk
        for (let i = 0; i < arrayOfOne.length; i++) {
            if (arrayOfOne[i] === 0) {
                if (!errorResult.includes("Jangkauan skor belum mencakup 0 sampai 100")) {
                    errorResult.push("Jangkauan skor belum mencakup 0 sampai 100")
                }
            }
        }

        // masukin infonya
        if (errorResult.length !== this.state.error.length) {
            this.setState({
                error: errorResult
            })
        }
        for (var i = 0; errorResult.length < i; i++) {
            if (this.state.error[i] !== errorResult[i]) {
                this.setState({
                    error: errorResult
                })
            }
        }
    }

    columns() {
        return (
            [{
                dataField: 'namaRating',
                editable: this.state.editMode,
                text: 'Nama',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { textAlign: 'left' };
                },
                validator: (newValue, row, column) => {
                    return this.validate(newValue, row, column);
                }
            }, {
                dataField: 'skorMinimal',
                editable: this.state.editMode,
                text: 'Skor Minimal',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { textAlign: 'left' };
                },
                validator: (newValue, row, column) => {
                    return this.validate(newValue, row, column);
                }
            }, {
                dataField: 'skorMaksimal',
                editable: this.state.editMode,
                text: 'Skor Maksimal',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { textAlign: 'left' };
                },
                validator: (newValue, row, column) => {
                    return this.validate(newValue, row, column);
                }
            }, {
                dataField: 'keteranganRating',
                editable: this.state.editMode,
                text: 'Keterangan',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { textAlign: 'left' };
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
                            recommended
                            onClick={() => this.hapus(row.idRating)}
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
            if (array[row].idRating !== action.idRating) {
                toReturn.push(array[row]);
            }
        }
        return toReturn;
    }

    async tambah() {
        const originalRow = this.state.rowList;

        const changedRow = this.insertItem(originalRow, {
            idRating: Math.floor(Math.random() * 100) + 100,
            namaRating: "",
            skorMinimal: "",
            skorMaksimal: "",
            keteranganRating: ""
        })

        this.setState({
            rowList: changedRow,
            rowChanged: true
        })
    }


    async hapus(id) {
        const originalRow = this.state.rowList;

        const changedRow = this.deleteItem(originalRow, {
            idRating: id
        })

        this.setState({
            rowList: changedRow,
            rowChanged: true
        })
    }

    hasEmptyRow() {
        var result = false;
        const error = this.state.error;
        const row = this.state.rowList;
        for (let i = 0; i < row.length; i++) {
            if (row[i].namaRating === "") {
                if (!error.includes("Terdapat nama rating yang kosong")) {
                    error.push("Terdapat nama rating yang kosong");
                }
                result = true;
            } else {
                if (error.includes("Terdapat nama rating yang kosong")) {
                    error.splice(error.indexOf("Terdapat nama rating yang kosong"), 1)
                }
            }
            if (row[i].skorMinimal === "") {
                if (!error.includes("Terdapat skor minimal yang kosong")) {
                    error.push("Terdapat skor minimal yang kosong");
                }
                result = true;
            } else {
                if (error.includes("Terdapat skor minimal yang kosong")) {
                    error.splice(error.indexOf("Terdapat skor minimal yang kosong"), 1)
                }
            }
            if (row[i].skorMaksimal === "") {
                if (!error.includes("Terdapat skor maksimal yang kosong")) {
                    error.push("Terdapat skor maksimal yang kosong");
                }
                result = true;
            } else {
                if (error.includes("Terdapat skor maksimal yang kosong")) {
                    error.splice(error.indexOf("Terdapat skor maksimal yang kosong"), 1)
                }
            }
        }
        return result;
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
        var error = this.hasEmptyRow();
        var addButton;
        if (this.state.editMode) {
            addButton = (
                <SirioButton
                    purple
                    recommended={!error}
                    disabled={error}
                    tooltip={error ? "Masih terdapat baris kosong" : undefined}
                    onClick={!error ? this.tambah : undefined}
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
        var hasError = this.state.error.length !== 0;
        if (this.state.editMode) {
            return (
                <div className="pr-3">
                    <SirioConfirmButton
                        purple
                        disabled={hasError}
                        tooltip={hasError ? "Selesaikan error terlebih dahulu" : undefined}
                        hover={!hasError}
                        classes="m-1"
                        modalTitle="Anda akan menyimpan perubahan konfigurasi Risk Rating"
                        onConfirm={this.handleSubmit}
                        customConfirmText="Konfirmasi"
                        customCancelText="Batal"
                    >
                        Simpan
                    </SirioConfirmButton>
                    <SirioWarningButton
                        red
                        recommended
                        modalTitle="Konfirmasi Pembatalan"
                        modalDesc="Seluruh perubahan konfigurasi Risk Rating yang belum tersimpan akan dihapus. Konfirmasi?"
                        onConfirm={() => window.location.reload(false)}
                        customConfirmText="Konfirmasi"
                        customCancelText="Kembali"
                    >
                        Batal
                </SirioWarningButton>
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
        return (
            <div className="row">
                <div className={this.state.editMode ? "col-9" : "col-12"}>
                    <SirioComponentHeader
                        title={(this.state.editMode ? "Konfigurasi " : "Daftar ") + "Risk Rating"}
                        headerButton={this.headerButton()}
                        subtitle={this.state.editMode && "Klik pada cell yang ingin anda ubah"}
                    />
                </div>
                <div className={this.state.editMode ? "col-3" : "d-none"}>

                </div>
                <div className={this.state.editMode ? "col-9" : "col-12"}>
                    <SirioTable
                        noHeader
                        data={this.state.rowList}
                        id='idRating'
                        columnsDefinition={column}
                        includeSearchBar
                        cellEdit={this.cellEdit}
                        indication="Belum ada Risk Rating yang terdaftar"
                    />
                    <div className="w-100 text-right">
                        {this.footerContent()}
                    </div>
                    {this.state.changeComplete &&
                        <SirioMessageButton
                            show
                            classes="d-none"
                            modalTitle="Perubahan Risk Rating Telah Disimpan"
                            customConfirmText="Kembali"
                            onClick={this.endNotification}
                        />
                    }
                </div>
                <div className={this.state.editMode ? "col-3" : "d-none"}>
                    <ComponentWrapper>
                        <div>
                            Aturan Risk Rating:
                            <ul>
                                <li>Mencakup 1 - 100</li>
                                <li>Jangkauan skor saling terpisah antar rating</li>
                                <li>Nama, skor minimal, dan skor maksimal harus terisi</li>
                            </ul>
                        </div>
                        {this.state.error.length !== 0 &&
                            <div>
                                Daftar kesalahan pengaturan:
                                <ul className="text-danger">
                                    {this.state.error.map((error, index) => <li key={index}>{error}</li>)}
                                </ul>
                            </div>
                        }
                    </ComponentWrapper>
                </div>
            </div>
        );
    }
}


export default withRouter(TableRiskRating);