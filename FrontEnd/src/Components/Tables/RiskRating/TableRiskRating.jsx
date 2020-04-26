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
            activeEditRowId: []
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

    endNotification() {
        this.setState({
            changeComplete: false
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
                text: '',
                editable: false,
                hidden: this.state ? !this.state.editMode : false,
                headerClasses: classes.colheader,
                classes: classes.rowItem,
                style: () => {
                    return { textAlign: 'center' }
                },
                formatter: (cell, row) => this.getButtonsFirst(row)
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
            rowList: changedRow
        })
    }


    async hapus(id) {
        const originalRow = this.state.rowList;

        const changedRow = this.deleteItem(originalRow, {
            idRating: id
        })

        this.setState({
            rowList: changedRow
        })
    }

    getButtonsFirst(row) {
        return (
            <SirioButton
                red
                onClick={() => this.hapus(row.idRating)}
            >
                Hapus
            </SirioButton>
        );
    }

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <>
                <SirioButton
                    purple
                    recommended={!this.state.editMode}
                    onClick={this.toggleEditMode}
                    classes="mx-1"
                >
                    {this.state.editMode ? "Nonaktifkan " : "Aktifkan "} Mode Edit
                </SirioButton>
                {this.state.editMode ?
                    <SirioButton
                        purple
                        recommended
                        onClick={this.tambah}
                        classes="mx-1"
                    >
                        Tambah
                    </SirioButton>
                    :
                    ""
                }
            </>
        )
    }

    footerContent() {
        if (this.state.editMode) {
            return (
                <div>
                    <SirioConfirmButton
                        purple
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
                        modalTitle="Konfirmasi Pembatalan"
                        modalDesc="Seluruh perubahan konfigurasi Risk Rating yang belum tersimpan akan dihapus. Konfirmasi?"
                        onConfirm={() => window.location.href = "/"}
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
            <>
                <SirioTable
                    title={(this.state.editMode ? "Konfigurasi " : "Daftar ") + "Risk Rating"}
                    data={this.state.rowList}
                    id='idRating'
                    columnsDefinition={column}
                    includeSearchBar
                    headerButton={this.headerButton()}
                    footerContent={this.footerContent()}
                    cellEdit={this.cellEdit}
                />
                {this.state.changeComplete &&
                    <SirioMessageButton
                        show
                        classes="d-none"
                        modalTitle="Perubahan Risk Rating Telah Disimpan"
                        customConfirmText="Kembali"
                        onClick={this.endNotification}
                    />
                }
            </>
        );
    }
}


export default withRouter(TableRiskRating);