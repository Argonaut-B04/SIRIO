import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelTugasPemeriksaan.module.css';
import SirioTable from '../SirioTable';
import TugasPemeriksaanService from '../../../Services/TugasPemeriksaanService';
import AuthenticationService from '../../../Services/AuthenticationService';
import { NavLink } from 'react-router-dom';
import SirioAxiosBase from '../../../Services/SirioAxiosBase';

export default class TabelTugasPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            role: AuthenticationService.getRole(),
            rowList: []
        }

        this.renderRows = this.renderRows.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    async renderRows() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");
        const response = await TugasPemeriksaanService.getTugasPemeriksaanByLoggedInUser();

        this.props.changeLoadingBody("Menampilkan data");
        this.setState({
            rowList: response.data.result
        }, this.props.contentFinishLoading())
    }

    getButtonsFirst(cell, row) {
        const idHasilPemeriksaan = row.idHasilPemeriksaan;
        const terdapatHasil = idHasilPemeriksaan !== null;
        if (terdapatHasil) {
            return (
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/detail",
                    state: {
                        id: idHasilPemeriksaan,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Detail Hasil
                    </SirioButton>
                </NavLink>
            )
        } else {
            return (
                <NavLink to={{
                    pathname: "/hasil-pemeriksaan/tambah",
                    state: {
                        id: row.id,
                    }
                }}>
                    <SirioButton
                        purple
                        recommended
                    >
                        Tambah Hasil
                    </SirioButton>
                </NavLink>
            )
        }
    }

    columnsQA = [
        {
            dataField: 'namaKantorCabang',
            text: 'TUGAS PEMERIKSAAN',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "25%", textAlign: 'left' };
            }
        }, {
            dataField: 'tanggalMulai',
            text: 'TANGGAL MULAI',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: () => {
                return { width: "20%" };
            },
            formatter: SirioAxiosBase.formatDateFromSirioDatePicker
        }, {
            dataField: 'tanggalSelesai',
            text: 'TANGGAL SELESAI',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: () => {
                return { width: "20%" };
            },
            formatter: SirioAxiosBase.formatDateFromSirioDatePicker
        }, {
            dataField: 'noData 1',
            text: 'Aksi Hasil',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsFirst,
            style: () => {
                return { textAlign: 'center' }
            }
        }];

    columns = [
        {
            dataField: 'namaKantorCabang',
            text: 'TUGAS PEMERIKSAAN',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'namaQA',
            text: 'QA DITUGASKAN',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: (colum, colIndex) => {
                return { width: "20%", textAlign: 'left' };
            }
        }, {
            dataField: 'tanggalMulai',
            text: 'TANGGAL MULAI',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: () => {
                return { width: "15%" };
            },
            formatter: SirioAxiosBase.formatDateFromSirioDatePicker
        }, {
            dataField: 'tanggalSelesai',
            text: 'TANGGAL SELESAI',
            sort: true,
            classes: classes.rowItem,
            headerClasses: classes.colheader,
            headerStyle: () => {
                return { width: "15%" };
            },
            formatter: SirioAxiosBase.formatDateFromSirioDatePicker
        }, {
            dataField: 'noData 1',
            text: 'Aksi Hasil',
            headerClasses: classes.colheader,
            classes: classes.rowItem,
            formatter: this.getButtonsFirst,
            headerStyle: () => {
                return { width: "15%" };
            },
            style: () => {
                return { textAlign: 'center' }
            }
        }];

    defaultSorted = [{
        dataField: 'tanggalMulai',
        order: 'asc'
    }];

    render() {
        return (
            <SirioTable
                title="Daftar Tugas Pemeriksaan"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.state.role === "QA Officer Operational Risk" ? this.columnsQA : this.columns}
                includeSearchBar
            />
        );
    }
}