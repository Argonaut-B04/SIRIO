import React from 'react';
import SirioButton from '../../Button/SirioButton';
import classes from './TabelRencanaPemeriksaan.module.css';
import SirioTable from '../SirioTable';
import RencanaPemeriksaanService from '../../../Services/RencanaPemeriksaanService';
import SirioMessageButton from "../../Button/ActionButton/SirioMessageButton";
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import AuthenticationService from '../../../Services/AuthenticationService';

class TabelRencanaPemeriksaan extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rowList: [],
            openNotification: true
        }

        this.renderRows = this.renderRows.bind(this);
        this.endNotification = this.endNotification.bind(this);
    }

    componentDidMount() {
        this.renderRows();
    }

    endNotification() {
        this.setState({
            openNotification: false
        })
    }

    async renderRows() {
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await RencanaPemeriksaanService.getRencanaPemeriksaanList();
        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
            rowList: response.data.result
        }, this.props.contentFinishLoading())
        
    }

    statusFormatter(cell) {
        switch (cell) {
            case 1:
                return (
                    <span style={{ color: '#black' }}>Draft</span>
                );
            case 3:
                return (
                    <span style={{ color: '#5DBCD2' }}>Selesai</span>
                );
            case 2:
                return (
                    <span style={{ color: '#6FCF97' }}>Sedang Dijalankan</span>
                );
            default:
                return (cell);
        }
    }

    getButtonsFirst(cell, row) {
        return (
            <NavLink to={{
                pathname: "/rencana-pemeriksaan/detail",
                state: {
                    id: row.id
                }
            }}>
                <SirioButton
                    purple
                >
                    Detail
                </SirioButton>
            </NavLink>
        )
    }

    getTahunFormatter(cell, row) {
        if (row.daftarTugasPemeriksaan[0] != null){
            const tanggalString = row.daftarTugasPemeriksaan[0].tanggalSelesai;
            const tahun = tanggalString.split("-")[0]
            return tahun;
        }else{
            return "-"
        }
    
        
    }

    getBulanFormatter(cell, row) {
        if(row.daftarTugasPemeriksaan[0] != null){
            const tanggalString = row.daftarTugasPemeriksaan[0].tanggalSelesai;
            const bulan = tanggalString.split("-")[1]
            var namaBulan = ["Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"];
            return namaBulan[bulan - 1];
        }else{
            return "-"
        }
        
    }

    columns = [{
        dataField: 'namaRencana',
        text: 'NAMA RENCANA',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "25%", textAlign: 'left' };
        }
    }, {
        dataField: 'noData1',
        dummyField: true,
        text: 'TAHUN',
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' }
        },
        formatter: (cell, row) => this.getTahunFormatter(cell, row)
    }, {
        dataField: 'noData2',
        text: 'BULAN',
        dummyField: true,
        sort: true,
        classes: classes.rowItem,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "15%", textAlign: 'left' }
        },
        formatter: (cell, row) => this.getBulanFormatter(cell, row)
    }, {
        dataField: 'status',
        text: 'STATUS',
        sort: true,
        classes: classes.rowItem,
        formatter: this.statusFormatter,
        headerClasses: classes.colheader,
        headerStyle: (colum, colIndex) => {
            return { width: "20%", textAlign: 'left' };
        }
    }, {
        dataField: 'noData3',
        text: '',
        headerClasses: classes.colheader,
        classes: classes.rowItem,
        style: () => {
            return { textAlign: 'center' }
        },
        formatter: this.getButtonsFirst
    }];


    defaultSorted = [{
        dataField: 'id',
        order: 'asc'
    }];

    headerButton() {
        return (
            <NavLink to={{
                pathname: "/rencana-pemeriksaan/tambah"
            }}>
                <SirioButton
                    purple recommended
                >
                    Tambah Rencana Pemeriksaan
                </SirioButton>
            </NavLink>
        )
    }
    render() {
        return (
            <>
            <SirioTable
                title="Daftar Rencana Pemeriksaan"
                data={this.state.rowList}
                id='id'
                columnsDefinition={this.columns}
                includeSearchBar
                headerButton={this.headerButton()}
            />
            {this.props.location.state && this.props.location.state.addSuccess && this.state.openNotification &&
            <SirioMessageButton
                show
                classes="d-none"
                modalTitle="Rencana Pemeriksaan berhasil Disimpan"
                customConfirmText="Tutup"
                onClick={this.endNotification}
            />
            }
            {this.props.location.state && this.props.location.state.deleteSuccess && this.state.openNotification &&
            <SirioMessageButton
                show
                classes="d-none"
                modalTitle="Rencana Pemeriksaan berhasil Dihapus"
                customConfirmText="Tutup"
                onClick={this.endNotification}
            />
            }
            {this.props.location.state && this.props.location.state.editSuccess && this.state.openNotification &&
            <SirioMessageButton
                show
                classes="d-none"
                modalTitle="Rencana Pemeriksaan berhasil Diubah"
                customConfirmText="Tutup"
                onClick={this.endNotification}
            />
            }
            {this.props.location.state && this.props.location.state.endSuccess && this.state.openNotification &&
            <SirioMessageButton
                show
                classes="d-none"
                modalTitle="Rencana Pemeriksaan berhasil diselesaikan"
                customConfirmText="Tutup"
                onClick={this.endNotification}
            />
            }
            </>
        );
    }
} 

export default withRouter(TabelRencanaPemeriksaan);
