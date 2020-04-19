import React from 'react';
import classes from '../../Tables/RegistrasiRisiko/TabelRisiko.module.css';
import SirioTable from '../../Tables/SirioTable';
import HierarkiRisikoService from '../../../Services/HierarkiRisikoService';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import SirioWarningButton from '../../Button/ActionButton/SirioWarningButton';
import SirioButton from '../../Button/SirioButton';
import SirioSelect from '../../Dropdown/SirioSelect';
import { Redirect } from 'react-router-dom';
import { withRouter } from 'react-router-dom';

class FormHierarkiRisiko extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            listOfOptionList: [],
            redirect: false,
        }

        this.renderRows = this.renderRows.bind(this);
        this.activeUbah = this.activeUbah.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        HierarkiRisikoService.getAllRisiko()
            .then(
                response => {
                    this.setState({
                        rowList: response.data.result
                    })
                }
            );
        HierarkiRisikoService.getByKategori()
            .then(
                response => {
                    this.setState({
                        listOfOptionList: response.data.result
                    })
                }
            )
    };

    columns() {
        return (
            [{
                dataField: 'nama',
                editable: false,
                text: 'NAMA',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { width: "30%", textAlign: 'center' };
                }
            }, {
                dataField: 'kategori',
                editable: false,
                text: 'KATEGORI',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { width: "20%", textAlign: 'center' };
                },
                style: () => {
                    return { textAlign: 'center' }
                },
            }, {
                dataField: 'noData 1',
                text: 'PARENT',
                isDummyField: true,
                sort: true,
                editable: false,
                formatter: (cell, row) => this.getOptions(row),
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { width: "25%", textAlign: 'center' };
                }
            }, {
                dataField: 'noData 2',
                text: 'EDIT',
                isDummyField: true,
                sort: true,
                editable: false,
                formatter: (cell, row) => this.activeUbah(row),
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                style: () => {
                    return { textAlign: 'center' }
                },
            }
            ]
        )
    }

    activeUbah(row) {
        // eslint-disable-next-line
        if (row.changeable == undefined) {
            row.changeable = false;
        }

        if (row.kategori === 1) {
            return "";
        }

        return (
            <SirioButton

                purple
                recommended
                onClick={() => {
                    row.changeable = !row.changeable;
                    this.forceUpdate();
                }}
            >
                {row.changeable ? "Selesai" : "Ubah Hierarki"}
            </SirioButton>
        )
    }

    getOptions(row) {
        if (row.kategori === 1) {
            return "-";
        }

        if (row.changeable) {
            const arrayIndexToGet = row.kategori - 2;
            const objectList = this.state.listOfOptionList[arrayIndexToGet];

            if (objectList) {
                const optionList = objectList.map(object => {
                    return ({
                        label: object.namaRisiko,
                        value: object.idRisiko
                    })
                })
                return (
                    <SirioSelect
                        name="parent"
                        value={row.parent}
                        handleChange={(name, event) => {
                            row.parent = event.value;
                            row.namaParent = event.label;
                            this.forceUpdate();
                        }}
                        options={optionList}
                    />
                )
            }
        } else {
            return row.namaParent
        }
    }

    footerContent() {
        return (
            <div>
                <SirioConfirmButton
                    purple
                    classes="m-1"
                    modalTitle="Anda akan menyimpan perubahan hierarki risiko"
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
                    modalDesc="Seluruh perubahan hierarki risiko yang belum tersimpan akan dihapus. Apakah Anda yakin?"
                    onConfirm={() => window.location.href = "/"}
                    customConfirmText="Konfirmasi"
                    customCancelText="Batal"
                >
                    Batal
            </SirioWarningButton>
            </div>
        )
    }

    handleSubmit() {
        HierarkiRisikoService.submitChanges(this.state.rowList)
            .then(() => this.setRedirect());
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/registrasi-risiko",
                state: {
                    editHierarkiSuccess: true
                }
            }} />
        }
    };

    // Fungsi untuk mendapatkan tombol di sisi kanan title
    headerButton() {
        return (
            <div>
                <SirioConfirmButton
                    purple
                    classes="m-1"
                    modalTitle="Anda akan menyimpan perubahan hierarki risiko"
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
                    modalDesc="Seluruh perubahan hierarki risiko yang belum tersimpan akan dihapus. Apakah Anda yakin?"
                    onConfirm={() => window.location.href = "/registrasi-risiko"}
                    customConfirmText="Konfirmasi"
                    customCancelText="Batal"
                >
                    Batal
            </SirioWarningButton>
            </div>
        )
    }

    render() {
        return (
            <>
            {this.renderRedirect()}
            <SirioTable
                title="Hierarki Semua Risiko"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns()}
                headerButton={this.headerButton()}
            />
            </>
        );
    }
} 

export default withRouter(FormHierarkiRisiko);