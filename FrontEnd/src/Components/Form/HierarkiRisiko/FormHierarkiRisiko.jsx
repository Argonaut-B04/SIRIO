import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from '../../Tables/RegistrasiRisiko/TabelRisiko.module.css';
import SirioTable from '../../Tables/SirioTable';
import HierarkiRisikoService from '../../../Services/HierarkiRisikoService';
import { NavLink } from 'react-router-dom';
import CellEditFactory, { Type } from 'react-bootstrap-table2-editor';
import SirioConfirmButton from '../../Button/ActionButton/SirioConfirmButton';
import SirioMessageButton from '../../Button/ActionButton/SirioMessageButton';
import SirioWarningButton from '../../Button/ActionButton/SirioWarningButton';
import { Redirect } from 'react-router-dom';
import SirioSelect from '../../Dropdown/SirioSelect';

export default class FormHierarkiRisiko extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            listOfOptionList: []
            // changeComplete: false,
            // editMode: true,
            // activeEditRowId: [],
            // optionList: [],
        }

        this.renderRows = this.renderRows.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
        // this.endNotification = this.endNotification.bind(this);
        // this.toggleEditMode = this.toggleEditMode.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    // endNotification() {
    //     this.setState({
    //         changeComplete: false
    //     })
    // }

    // toggleEditMode() {
    //     this.setState({
    //         editMode: !this.state.editMode
    //     })
    // }

    // renderRedirect = () => {
    //     if (this.state.redirect) {
    //         return <Redirect to={{
    //             pathname: "/registrasi-risiko",
    //             state: {
    //                 editSuccess: true
    //             }
    //         }} />
    //     }
    // };

    // handleSubmit() {
    //     HierarkiRisikoService.submitChanges(this.state.rowList)
    //         .then(() => {
    //             this.renderRows()
    //             this.setState({
    //                 changeComplete: true,
    //                 editMode: false
    //             })
    //             this.renderRedirect()
    //         });
    // }

    async renderRows() {
        HierarkiRisikoService.getAllRisiko()
            .then(
                response => {
                    this.setState({
                        rowList: response.data.result
                    })
                    this.renderOptions();
                }
            );
    };

    async renderOptions() {
        console.log(this.state.rowList)
    }

    // getButtons(cell, row) {
    //     console.log(row)
    //     if (this) {
    //         return (
    //             <NavLink to={{
    //                 pathname: "/registrasi-risiko/ubah-hierarki/ubah",
    //                 state: {
    //                     id: row.idRisiko,
    //                 }
    //             }}>
    //                 <SirioButton
    //                     purple
    //                 >
    //                     Ubah Hierarki
    //                 </SirioButton>
    //             </NavLink>
    //         )
    //     }
    // }

    // parentFormatter(cell, row) {
    //     if (row.parent === null) {
    //         return "-"
    //     } else {
    //         return row.namaParent
    //     }
    // }

    // async getOptions(cell, row) {
    //     const response = await HierarkiRisikoService.getParentByKategori(row.id);
    //     const optionList = response.data.result.map(object => {
    //         return (
    //             {
    //                 label: object.namaRisiko,
    //                 value: object.idRisiko
    //             }
    //         )
    //     });
    //     console.log(optionList)
    //     this.setState({
    //         optionList: optionList
    //     })
    // }

    getOptions(cell, row) {
        // var optionList;
        // const hasil = HierarkiRisikoService.getParentByKategori(row.id).then(
        //     response => {
        //         if (response.data.result) {
        //             optionList = response.data.result.map(object => {
        //                 return (
        //                     {
        //                         label: object.namaRisiko,
        //                         value: object.idRisiko
        //                     }
        //                 )
        //             });
        //         } else {
        //             return "-"
        //         }
        //     }
        // );
        // console.log(optionList)
        // return (
        //     <SirioSelect
        //         // name={this.props.name}
        //         // value={this.props.value}
        //         // handleChange={this.props.handleChange}
        //         optionList={optionList}
        //     />
        // )
        return ""
    }

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
                    return { width: "30%", textAlign: 'left' };
                }
            }, {
                dataField: 'kategori',
                editable: false,
                text: 'KATEGORI',
                sort: true,
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { width: "25%", textAlign: 'center' };
                }
            }, {
                dataField: 'noData 1',
                // name: 'parent',
                text: 'PARENT',
                isDummyField: true,
                // editor: {
                //     type: Type.SELECT,
                //     name: 'parent',
                //     // getOptions: this.getOptions
                // },
                sort: true,
                editable: false,
                formatter: (cell, row) => this.getOptions(cell, row),
                classes: classes.rowItem,
                headerClasses: classes.colheader,
                headerStyle: (colum, colIndex) => {
                    return { width: "20%", textAlign: 'center' };
                }
            },
                // {
                //     dataField: 'noData 1',
                //     text: '',
                //     headerClasses: classes.colheader,
                //     classes: classes.rowItem,
                //     style: () => {
                //         return { textAlign: 'center' }
                //     },
                //     formatter: this.getButtons
                //     }
            ]
        )
    }

    cellEdit = CellEditFactory({
        mode: 'click',
        blurToSave: true,
    })

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
                    customCancelText="Kembali"
                >
                    Batal
            </SirioWarningButton>
            </div>
        )
    }

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
                    customCancelText="Kembali"
                >
                    Batal
            </SirioWarningButton>
            </div>
        )
    }

    render() {
        return (
            <SirioTable
                title="Hierarki Semua Risiko"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns()}
                includeSearchBar
                headerButton={this.headerButton()}
                cellEdit={this.cellEdit}
            />
        );
    }
} 